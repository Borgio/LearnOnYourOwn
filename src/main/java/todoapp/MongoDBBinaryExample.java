package todoapp;

import java.io.File;
import java.io.IOException;

import com.mongodb.DB;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;

public class MongoDBBinaryExample {
	public static void main(String[] args) throws IOException {
		MongoClient mongo = new MongoClient("localhost", 27017);
		DB db = mongo.getDB("GMIT");
		// Save a image in DB
		saveImageIntoMongoDB(db);
		// Get a image from DB
		getSingleImageExample(db);
		// Get all images from DB
		listAllImages(db);
		//save the image to the file system
		saveToFileSystem(db);
		// Delete images from DB
		//deleteImageFromMongoDB(db);

		// Verifying if image was deleted or not
		getSingleImageExample(db);
	}

	private static void saveImageIntoMongoDB(DB db) throws IOException {
		String dbFileName = "keyboard";
		File imageFile = new File("keyboard.jpg");
		GridFS gfsPhoto = new GridFS(db, "photo");
		GridFSInputFile gfsFile = gfsPhoto.createFile(imageFile);
		gfsFile.setFilename(dbFileName);
		gfsFile.save();
	}

	private static void getSingleImageExample(DB db) {
		String newFileName = "keyboard";
		GridFS gfsPhoto = new GridFS(db, "photo");
		GridFSDBFile imageForOutput = gfsPhoto.findOne(newFileName);
		System.out.println("Printing image");
		System.out.println(imageForOutput);
	}

	private static void listAllImages(DB db) {
		GridFS gfsPhoto = new GridFS(db, "photo");
		DBCursor cursor = gfsPhoto.getFileList();
		while (cursor.hasNext()) {
			System.out.println(cursor.next());
		}
	}

	private static void saveToFileSystem(DB db) throws IOException {
		String dbFileName = "keyboard";
		GridFS gfsPhoto = new GridFS(db, "photo");
		GridFSDBFile imageForOutput = gfsPhoto.findOne(dbFileName);
		imageForOutput.writeTo("new.png");
	}

	private static void deleteImageFromMongoDB(DB db) {
		String dbFileName = "keyboard";
		GridFS gfsPhoto = new GridFS(db, "photo");
		gfsPhoto.remove(gfsPhoto.findOne(dbFileName));
	}
}