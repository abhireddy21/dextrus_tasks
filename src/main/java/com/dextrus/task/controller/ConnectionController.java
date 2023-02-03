package com.dextrus.task.controller;


import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.dextrus.task.model.ConnectionRequest;
import com.dextrus.task.model.TableDescription;
import com.dextrus.task.model.TableProperties;
import com.dextrus.task.service.ConnectionService;

@RestController
@RequestMapping("/map")
public class ConnectionController {
	@Autowired
	ConnectionService connectionService;
	//(Task-1)makes test connections
	@PostMapping("/")
	public ResponseEntity<String> connectDB(@RequestBody ConnectionRequest connectionRequest) {
		String response = connectionService.connection(connectionRequest);
		
		if(response=="SUCCESS") {
			return new ResponseEntity<String>("CONNECTED",HttpStatus.OK);
		}
		else {
			return new ResponseEntity<String>("FAILED",HttpStatus.NOT_FOUND);
		}
	}
	//(Task-2)Getting List of Catalogs 
	@GetMapping("/listC")
	public ResponseEntity<List<String>> listOfCatalogs(@RequestBody ConnectionRequest connectionRequest, ConnectionService connectionService) {
	 List<String> la = connectionService.connectGetCatalogs(connectionRequest);
			return new ResponseEntity<List<String>>(la,HttpStatus.OK);
	}
	
	//(Task-3)Getting List of Schemas  
//	@GetMapping("/{catalog}")
	@GetMapping("/listS")
	public ResponseEntity<List<String>> listOfSchemas(@RequestBody ConnectionRequest connectionRequest, ConnectionService connectionService){
		List<String> la = connectionService.connectGetListOfSchemas(connectionRequest);
		return new ResponseEntity<List<String>>(la,HttpStatus.OK);
	}
	
	//(Task-4)Getting List of tables
//	@GetMapping("/{catalog}/{schema}")
		@GetMapping("/listT")
		public ResponseEntity<List<TableProperties>> listOfTables(@RequestBody ConnectionRequest connectionRequest, ConnectionService connectionService){
			ArrayList<TableProperties> la = connectionService.connectGetListOfTables(connectionRequest);
			return new ResponseEntity<List<TableProperties>>(la,HttpStatus.OK);
		}
	
	//(Task-5)Describe Table
//	@GetMapping("/{catalog}/{schema}/{table}")
		@GetMapping("/descT")
		public ResponseEntity<List<TableDescription>> describeTables(@RequestBody ConnectionRequest connectionRequest, ConnectionService connectionService){
			ArrayList<TableDescription> la = connectionService.connectGetDescOfTables(connectionRequest);
			return new ResponseEntity<List<TableDescription>>(la,HttpStatus.OK);
		} 
	
	//(Task-6)
		@GetMapping("/query")
		public ResponseEntity<ArrayList<ArrayList<String>>> getQueryDetails(@RequestBody ConnectionRequest connectionRequest, ConnectionService connectionService){
			ArrayList<ArrayList<String>> la = connectionService.getMetadataOfQuery(connectionRequest);
			return new ResponseEntity<ArrayList<ArrayList<String>>>(la, HttpStatus.OK);
		}	
	
	
	//(#Task-7)
		@GetMapping("/search")
		public ResponseEntity<List<TableProperties>> getTablesByPattern(@RequestBody ConnectionRequest connectionRequest, ConnectionService connectionService){
			List<TableProperties> viewsAndTables = connectionService.getTablesAndViewsByPattern(connectionRequest);
			return new ResponseEntity<List<TableProperties>>(viewsAndTables, HttpStatus.OK);
		}
}
