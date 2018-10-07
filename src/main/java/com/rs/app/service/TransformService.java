package com.rs.app.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.stereotype.Service;

/**
 * 
 * @author ramesh
 *
 */
@Service
public class TransformService {

	public String trasnformFile(String filePath) throws IOException {
		String content = new String(Files.readAllBytes(Paths.get(filePath)));
		return content + "/n-Ramesh";
	}
}
