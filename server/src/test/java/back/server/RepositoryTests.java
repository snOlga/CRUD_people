package back.server;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import back.server.citizens.Citizen;
import back.server.citizens.Country;
import back.server.citizens.exceptions.ColorFormatException;
import back.server.citizens.exceptions.PassportIDUniqueException;
import back.server.citizens.exceptions.UnrealHumanHeightException;
import back.server.repository.CitizenRepository;
import java.time.LocalDate;

@SpringBootTest
class RepositoryTests {

	@Test
	void readOneCitizenRepository() {
		CitizenRepository repo = new CitizenRepository();
		Citizen citizen = (Citizen) repo.find(1);
		assertEquals("David", citizen.getName());
	}

	@Test
	void addCitizenRepository() throws ColorFormatException, UnrealHumanHeightException, PassportIDUniqueException {
		CitizenRepository repo = new CitizenRepository();
		Citizen citizen = new Citizen("David", (byte) 1, "#000000", "#000000", (short) 160,
				LocalDate.parse("2000-10-01"),
				101l,
				Country.FRANCE);
		repo.add(citizen);
		assertNotEquals(null, citizen.getId());
	}

	@Test
	void deleteCitizenFromRepository() throws ColorFormatException, UnrealHumanHeightException, PassportIDUniqueException {
		CitizenRepository repo = new CitizenRepository();
		Citizen citizen = new Citizen("David", (byte) 0, "#000000", "#000000", (short) 160,
				LocalDate.parse("2000-10-01"),
				10l,
				Country.FRANCE);
		repo.add(citizen);
		repo.delete(citizen);
		assertEquals(repo.find(citizen.getId()), null);
	}

	@Test
	void updatedCitizenFromRepository() throws ColorFormatException, UnrealHumanHeightException, PassportIDUniqueException {
		CitizenRepository repo = new CitizenRepository();
		Citizen citizen = new Citizen("Emma", (byte) 0, "#000000", "#000000", (short) 160,
				LocalDate.parse("2000-10-01"),
				10l, Country.FRANCE);
		repo.add(citizen);
		citizen.setHairColor("#ff0000");
		repo.update(citizen);
		Citizen fromRepo = (Citizen) repo.find(citizen.getId());
		assertEquals(fromRepo.getId(), citizen.getId());
		assertEquals(fromRepo.getHairColor(), citizen.getHairColor());
	}
}
