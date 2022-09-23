package bookshibernateapp.util;

import java.util.List;
import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

//import org.hibernate.mapping.List;

public class App {

	public static void main(String[] args) {

		System.out.println("main running...");
		Books book = new Books();
		// INSERT VALUES TO OBJECT TO SAVE IT ON DATABASE
		book.setAuthor("frank f");
		book.setName("spring boot");
		book.setPublisher("mega publishers");
		book.setPrice(130.0);
		// save books
		saveBook(book);
		// display books list
		displayAllBooks(getAllBooks());
		// search book by id
		getbookbyid(3);
		// delete book by id
		deleteById(4);
		// update ,update name for this example with id =1
		Books bookupdate = new Books();
		bookupdate.setName("new_book_name");
		updatebook(1, bookupdate);

	}

	// return session object
	static Session getSession() {
		// configuration
		Configuration conf = new Configuration();
		conf.configure("hibernate.cfg.xml");
		// session
		Session session = conf.buildSessionFactory().openSession();

		return session;
	}
	//return all books list .it accepts from getAllbooks() method
	static void displayAllBooks(List<Books> books) {
		for (Books bk : books) {
			System.out.println("id: " + bk.getId() + "  name: " + bk.getName() + "," + " Author: " + bk.getAuthor()
					+ "," + " publisher: " + bk.getPublisher() + "," + " Price:" + bk.getPrice() + ".");
		}
	}
	// update book name

	private static void updatebook(int id, Books bookupdate) {

		Session session = getSession();
		Transaction transaction = session.beginTransaction();
		Books book = (Books) session.createQuery("From Books WHERE id=" + id).getSingleResult();
		book.setName(bookupdate.getName());
		// transaction commit
		transaction.commit();
		// session closed
		session.close();

	}

	// delete by id
	private static void deleteById(int id) {

		Session session = getSession();
		Transaction transaction = session.beginTransaction();
		Query query = session.createQuery("DELETE From Books WHERE id=" + id);

		int row = query.executeUpdate();
		System.out.println("ROWS AFFECTED + " + row);
		// transaction commit
		transaction.commit();
		// session closed
		session.close();

	}
	// get book by id

	private static void getbookbyid(int id) {

		Session session = getSession();
		Transaction transaction = session.beginTransaction();
		Books bk = (Books) session.createQuery("From Books WHERE id=" + id).getSingleResult();
		// transaction commit
		transaction.commit();
		// session closed
		session.close();

		System.out.println("id: " + bk.getId() + "  name: " + bk.getName() + "," + " Author: " + bk.getAuthor() + ","
				+ " publisher: " + bk.getPublisher() + "," + " Price:" + bk.getPrice() + ".");

	}

	// fetch all data from a table
	static List<Books> getAllBooks() {
		Session session = getSession();
		Transaction transaction = session.beginTransaction();
		List<Books> books = session.createQuery("From Books").list();
		// transaction commit
		transaction.commit();
		// session closed
		session.close();
		return books;
	}

	// save object to data base
	static void saveBook(Books book) {

		Session session = getSession();
		// transaction
		Transaction transaction = session.beginTransaction();
		// save object to database
		session.save(book);
		// transaction commit
		transaction.commit();
		// session closed
		session.close();

		displayAllBooks(getAllBooks());

	}
}
