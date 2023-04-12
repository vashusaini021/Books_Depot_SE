package com.booksdepot.group7;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.booksdepot.group7.models.CartModel;
import com.booksdepot.group7.models.UsersModel;
import com.booksdepot.group7.repositories.BooksRepo;
import com.booksdepot.group7.repositories.UsersRepo;

@SpringBootApplication
public class BooksDepotApplication {

	public static void main(String[] args) {
		SpringApplication.run(BooksDepotApplication.class, args);
	}

	@Bean
	ApplicationRunner init(BooksRepo booksRepository, UsersRepo userRepo) {
		
		ApplicationRunner runner = new ApplicationRunner() {
			@Override
			public void run(ApplicationArguments args) throws Exception {	
				booksRepository.saveAll(JSONReaderHelper.getInstance().booksList);
				
				UsersModel user1 = new UsersModel("vashusaini021", "Vasu", "Saini", "1111");
				UsersModel user2 = new UsersModel("laibaAyyaz021","Laiba","Ayyaz", "1111");
				UsersModel user3 = new UsersModel("sabiyasabiya","Sabiya","Sabiya", "1111");
				UsersModel user4 = new UsersModel("tusharsharma", "Tushar","Sharma","1111");
								
				userRepo.save(user1);
				userRepo.save(user2);
				userRepo.save(user3);
				userRepo.save(user4);
			}
		};
		return runner;
		
	}

}
