//package com.nastech.upmureport.controller;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PatchMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.nastech.upmureport.domain.dto.PdirDto;
//import com.nastech.upmureport.service.PdirService;
//
//
//@RestController
//@RequestMapping(value = "/api/pdir")
//public class PdirController {
//	
//	@Autowired
//	private PdirService ds;
//	
//	@GetMapping(value = "/list")
//	ResponseEntity<List<PdirDto>> list(@RequestParam(value = "pid", required=true) String pid) {
//		return ResponseEntity.ok().body(ds.listByPid(pid));
//	}
//	
//	@PostMapping(value ="/register")
//	ResponseEntity<PdirDto> register(@RequestBody PdirDto pDto) {
//		return ResponseEntity.ok().body(ds.register(pDto));
//	}
//	
//	@PutMapping(value ="/correct")
//	ResponseEntity<PdirDto> correct(@RequestBody PdirDto pDto) {
//		return ResponseEntity.ok().body(ds.correct(pDto, "수정"));
//	}
//	
//	@PatchMapping(value = "/disable")
//	ResponseEntity<PdirDto> disable(@RequestBody PdirDto pDto)  {
//		return ResponseEntity.ok().body(ds.disable(pDto));
//	}
//	
////	@PostMapping(value = "/dirs")
////	ResponseEntity<List<DirDto>> dirs(@RequestBody Map<String, String> formData) {
////		List<DirDto> dtoList = projectService.findDirsByProjId(formData.get("projId"));
////
////		return new ResponseEntity<List<DirDto>>(dtoList, HttpStatus.OK);
////	}
////	
////	@PostMapping(value = "/registerDir", consumes = MediaType.APPLICATION_JSON_VALUE, produces = {MediaType.APPLICATION_JSON_VALUE})
////	ResponseEntity<String> registerDir(@RequestBody Map<String, String> formData) {
////		DirDto dto = DirDto.builder()
////				.projId(formData.get("projId"))
////				.dirName(formData.get("dirName"))
////				.userId(formData.get("userId"))
////				.parentDirId(formData.get("parentId"))
////				.build();
////		Dir rtn = projectService.registerDir(dto);
////
////		return new ResponseEntity<String>(rtn.getDirId().toString(), HttpStatus.OK);
////	}
////	
////	@PostMapping(value = "/disableDir", consumes = MediaType.APPLICATION_JSON_VALUE, produces = {MediaType.APPLICATION_JSON_VALUE})
////	ResponseEntity<Boolean> disableDir(@RequestBody Map<String, String> formData) {
////		DirDto dto = DirDto.builder()
////				.projId(formData.get("projId"))
////				.dirId(formData.get("dirId"))
////				.userId(formData.get("userId"))
////				.build();
////		projectService.disableDir(dto);
////
////		return new ResponseEntity<Boolean>(true, HttpStatus.OK);
////	}
////	
////	@PostMapping(value = "/correctDir", consumes = MediaType.APPLICATION_JSON_VALUE)
////	ResponseEntity<Boolean> correctDir(@RequestBody Map<String, String> formData) {
////		if (projectService.registerDir(
////				DirDto.builder()
////				.dirId(formData.get("dirId"))
////				.dirName(formData.get("dirName"))
////				.parentDirId(formData.get("parentId"))
////				.build()) != null) 
////			return new ResponseEntity<Boolean>(true, HttpStatus.OK);
////		else 
////			return new ResponseEntity<Boolean>(false, HttpStatus.OK);
////	}
//}
