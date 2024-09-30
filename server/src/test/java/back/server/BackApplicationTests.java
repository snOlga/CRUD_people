package back.server;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.ArgumentMatchers.nullable;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import back.server.citizens.Citizen;
import back.server.citizens.Country;
import back.server.repository.CitizenRepository;

@SpringBootTest
class BackApplicationTests {

	@Test
	void readOneCitizenRepository() {
		CitizenRepository repo = new CitizenRepository();
		Citizen citizen = (Citizen) repo.find(1);
		assertEquals("David", citizen.getName());
	}

	@Test
	void addCitizenRepository() {
		CitizenRepository repo = new CitizenRepository();
		Citizen citizen = new Citizen("Margo", "Green", "Black", (short) 160, new Date(), 10l, Country.FRANCE);
		repo.add(citizen);
		assertNotEquals(null, citizen.getId());
	}

	@Test
	void deleteCitizenFromRepository() {
		CitizenRepository repo = new CitizenRepository();
		Citizen citizen = new Citizen("Liza", "Green", "Black", (short) 160, new Date(), 10l, Country.FRANCE);
		repo.add(citizen);
		repo.delete(citizen);
		assertEquals(repo.find(citizen.getId()), null);
	}

	@Test
	void updatedCitizenFromRepository() {
		CitizenRepository repo = new CitizenRepository();
		Citizen citizen = new Citizen("Emma", "Green", "Black", (short) 160, new Date(), 10l, Country.FRANCE);
		repo.add(citizen);
		citizen.setHairColor("Red");
		repo.update(citizen);
		Citizen fromRepo = (Citizen) repo.find(citizen.getId());
		assertEquals(fromRepo.getId(), citizen.getId());
		assertEquals(fromRepo.getHairColor(), citizen.getHairColor());
	}
}
