package back.server.service;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import back.server.DTO.CitizenDTO;
import back.server.util.AmountCitizenException;
import back.server.util.ColorFormatException;
import back.server.util.PassportIDUniqueException;
import back.server.util.SQLinjectionException;
import back.server.util.UnrealHumanHeightException;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;

@Service
public class FileCitizenService {

    @Autowired
    private MinioClient minioClient;
    @Value("${minio.bucket-name}")
    private String bucketName;
    @Autowired
    private CitizenService citizenService;
    private long filenameID = 0;

    @Transactional
    public String importFromFile(MultipartFile file) throws IOException, NumberFormatException, ColorFormatException,
            UnrealHumanHeightException, PassportIDUniqueException, SQLinjectionException, AmountCitizenException {
        CitizenDTO[] citizens = parseJsonFile(file);
        citizenService.saveAll(citizens);
        String newFilename = saveFile(file);
        return newFilename;
    }

    @Transactional
    public String deleteFromFile(MultipartFile file) throws IOException, NumberFormatException, ColorFormatException,
            UnrealHumanHeightException, PassportIDUniqueException, SQLinjectionException, AmountCitizenException {
        CitizenDTO[] citizens = parseJsonFile(file);
        citizenService.deleteAll(citizens);
        String newFilename = saveFile(file);
        return newFilename;
    }

    private CitizenDTO[] parseJsonFile(MultipartFile file) throws NumberFormatException, ColorFormatException,
            UnrealHumanHeightException, PassportIDUniqueException, SQLinjectionException, IOException {
        String content = new String(file.getBytes());
        Gson gson = new Gson();
        Type listType = new TypeToken<List<Map<String, String>>>() {
        }.getType();
        List<Map<String, String>> jsonArray = gson.fromJson(content, listType);
        CitizenDTO[] citizens = convertJsonToCitizenArray(jsonArray);
        return citizens;
    }

    @Transactional(propagation = Propagation.NESTED)
    private String saveFile(MultipartFile file) throws IOException {
        String objectName = filenameID++ + "_" + file.getOriginalFilename();
        InputStream inputStream = file.getInputStream();
        try {
            minioClient.putObject(PutObjectArgs.builder().bucket(bucketName).object(objectName)
                    .stream(inputStream, -1, 10485760).build());

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return objectName;
    }

    private CitizenDTO[] convertJsonToCitizenArray(List<Map<String, String>> json) throws NumberFormatException,
            ColorFormatException, UnrealHumanHeightException, PassportIDUniqueException, SQLinjectionException {
        CitizenDTO[] citizens = new CitizenDTO[json.size()];
        for (int i = 0; i < json.size(); i++) {
            citizens[i] = new CitizenDTO(json.get(i));
        }
        return citizens;
    }
}
