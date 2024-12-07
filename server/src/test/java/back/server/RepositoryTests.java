package back.server;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import back.server.enums.Country;
import back.server.model.Citizen;
import back.server.repository.CitizenRepository;
import back.server.util.ColorFormatException;
import back.server.util.PassportIDUniqueException;
import back.server.util.SQLinjectionException;
import back.server.util.UnrealHumanHeightException;

import java.time.LocalDate;

@SpringBootTest
class RepositoryTests {
	@Autowired
	private CitizenRepository repo;

	@Test
	void readOneCitizenRepository() {
		Citizen citizen = (Citizen) repo.findById(1L).get();
		assertEquals("David", citizen.getName());
	}

	@Test
	void addCitizenRepository()
			throws ColorFormatException, UnrealHumanHeightException, PassportIDUniqueException, SQLinjectionException {
		Citizen citizen = new Citizen("David", (byte) 1, "#000000", "#000000", (short) 160,
				LocalDate.parse("2000-10-01"),
				101L,
				Country.FRANCE);
		repo.save(citizen);
		assertNotEquals(null, citizen.getId());
	}

	@Test
	void deleteCitizenFromRepository()
			throws ColorFormatException, UnrealHumanHeightException, PassportIDUniqueException, SQLinjectionException {
		Citizen citizen = new Citizen("David", (byte) 0, "#000000", "#000000", (short) 160,
				LocalDate.parse("2000-10-01"),
				10L,
				Country.FRANCE);
		repo.save(citizen);
		repo.delete(citizen);
		assertEquals(repo.findById(citizen.getId()).orElse(null), null);
	}

	@Test
	void updatedCitizenFromRepository()
			throws ColorFormatException, UnrealHumanHeightException, PassportIDUniqueException, SQLinjectionException {
		Citizen citizen = new Citizen("Emma", (byte) 0, "#000000", "#000000", (short) 160,
				LocalDate.parse("2000-10-01"),
				10L, Country.FRANCE);
		repo.save(citizen);
		citizen.setHairColor("#ff0000");
		repo.save(citizen);
		Citizen fromRepo = (Citizen) repo.findById(citizen.getId()).get();
		assertEquals(fromRepo.getId(), citizen.getId());
		assertEquals(fromRepo.getHairColor(), citizen.getHairColor());
	}

	@Test
	void addTwoNotUniquePassports()
			throws ColorFormatException, UnrealHumanHeightException, PassportIDUniqueException, SQLinjectionException {
		try {
			Citizen citizen1 = new Citizen("Emma", (byte) 0, "#000000", "#000000", (short) 160,
					LocalDate.parse("2000-10-01"),
					99L, Country.FRANCE);
			Citizen citizen2 = new Citizen("Stella", (byte) 0, "#000000", "#000000", (short) 160,
					LocalDate.parse("2000-10-01"),
					99L, Country.FRANCE);
			repo.save(citizen1);
			repo.save(citizen2);
			fail();
		} catch (Exception e) {
			// nothing
		}
	}
}
