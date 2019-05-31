package com.nastech.upmureport.feature.project.service;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.math.BigInteger;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.nastech.upmureport.common.support.Utils;
import com.nastech.upmureport.feature.project.domain.dto.AttachmentDto;
import com.nastech.upmureport.feature.project.domain.entity.Attachment;
import com.nastech.upmureport.feature.project.domain.entity.Pdir;
import com.nastech.upmureport.feature.project.domain.enums.LogState;
import com.nastech.upmureport.feature.project.repo.AttachmentRepository;
import com.nastech.upmureport.feature.project.repo.PdirRepository;

import lombok.RequiredArgsConstructor;

/*
 * @Author : 김윤상		2019.05.22. 
 * 
 * @Description : 첨부파일 ( Attachment ) CRUD 비즈니스 로직  
 */
@Service
@RequiredArgsConstructor
public class AttachmentService {
	private final String UPLOAD_PATH = "C:\\\\Users\\\\nastech\\\\Desktop\\\\attachment";
	private final String PREFIX_URL = "localhost.com";
	
	@SuppressWarnings("unused")
	private static final Log LOG = LogFactory.getLog(AttachmentService.class);

	private final AttachmentRepository attachmentRepository;
	private final PdirRepository pdirRepository;
	private final PLogService pLogService;

	/* 첨부파일 저장 */
	public AttachmentDto.AttachmentResDto saveAttachment(MultipartFile file, AttachmentDto.AttachmentReqDto attachmentReqDto) throws Exception {

		File destinationFile = saveFile(file);  // 실제 서버에 파일 저장
		String fileName = file.getOriginalFilename(); // 첨부 파일 이름 가져오기
		Pdir pdir = pdirRepository.findById(Utils.StrToBigInt(attachmentReqDto.getPdir())).get(); // 첨부파일이 저장 될 디렉토리 조회

		Attachment attachment = attachmentBuilder(destinationFile, fileName, pdir, attachmentReqDto, file); // 첨부파일 빌드
		
		attachment = attachmentRepository.save(attachment); // 첨부파일 DB 저장
		pLogService.createAttachmentLog(attachment, LogState.CREATE); // 첨부파일 추가 로그 생성

		return attachment2AttachmentResDto(attachment); // 저장 된 첨부파일 DTO 반환
	}

	/* 첨부파일 리스트 조회 */
	public List<AttachmentDto.AttachmentResDto> getAttachment(BigInteger pdirId) {

		Pdir pdir = pdirRepository.findById(pdirId).get(); // 첨부파일 리스트를 조회 할 디렉토리 조회 
		List<Attachment> attachments = attachmentRepository.findByDid(pdir); // 해당 디렉토리의 첨부파일 리스트 DB 조회
		
		return  attachments2AttachmentResDtos(attachments); // 조회 된 첨부파일 엔티티 리스트 -> 첨부파일 DTO 변환  -> 반환
	}
	
	/* 첨부파일 삭제 */
	public List<AttachmentDto.AttachmentResDto> deleteAttachment(String attachmentId) {
		Attachment attachment = attachmentRepository.findById(Utils.StrToBigInt(attachmentId)).get(); // 삭제 할 첨부파일 조회
		
		attachment.deleteAttachment(); // 첨부파일 삭제 플래그 변경
		
		attachment = attachmentRepository.save(attachment); // 첨부 파일 삭제 플래그 변경 후 DB 업데이트
		
		pLogService.createAttachmentLog(attachment, LogState.DELETE); // 첨부파일 삭제 로그 생성
		
		return getAttachment(attachment.getPdir().getDid()); // 해당 첨부파일이 삭제 되고 난 후 디렉토리의 첨부파일 리스트 반환
	}
	
	/* 첨부파일 하나 다운로드 */
	public ResponseEntity<Resource> downloadAttachment(String attachmentId, HttpServletResponse resp) throws Exception {
		
		Attachment attachment = attachmentRepository.findById(Utils.StrToBigInt(attachmentId)).get(); // 다운로드 할 첨부파일 조회
		
		Path filePath = Paths.get(attachment.getLocalPath()).normalize(); // 다운로드 될 첨부파일 경로 생성
		
		Resource resource = new UrlResource(filePath.toUri()); // 반환 할 첨부파일 리소스 생성		
		
		return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(attachment.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);  // 헤더값 설정과  response body에 다운로드 될 파일 넣어서 반환 
	}
	
	
	/* 첨부파일 여러개 다운로드 zip 형식 */ 
	public ResponseEntity<byte[]> downloadAttachmentGroup(List<String> attachmentIds) throws Exception {		

		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(); 
		BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(byteArrayOutputStream);
		ZipOutputStream zipOutputStream = new ZipOutputStream(bufferedOutputStream); // zip 파일로 만들기 위한 스트림 객체들 생성
		List<Attachment> attachments = new ArrayList<Attachment>(); // 다운로드 될 첨부파일들 리스트 객체 생성
				
		attachmentIds.forEach(attachmentId -> { // 다운로드 될 첨부파일 들 조회 후 리스트에 넣음
			Attachment attachment = attachmentRepository.findById(Utils.StrToBigInt(attachmentId)).get();
			attachments.add(attachment);
		});	
		
		attachments.forEach(attachment -> { // 각 첨부파일 루프
			try {
				File fileToZip = new File(attachment.getLocalPath());  // 첨부파일 객체
				FileInputStream fileInputStream = new FileInputStream(fileToZip); // 해당 첨부파일을 읽을 스트림 생성
	            ZipEntry zipEntry = new ZipEntry(fileToZip.getName()); // 해당 첨부파일에 대한 zip entry 객체 생성
	            zipOutputStream.putNextEntry(zipEntry); // zip entry 객체를 zip 스트림에 추가
	            
	            IOUtils.copy(fileInputStream, zipOutputStream); // 첨부 파일을 읽은 파일을 보낼 스트림에 복사
	            
	            fileInputStream.close();
	            zipOutputStream.closeEntry();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		
		if (zipOutputStream != null) // zip 스트림 닫기
	    {
	      zipOutputStream.finish();
	      zipOutputStream.flush();
	      IOUtils.closeQuietly(zipOutputStream);
	    }
		
		// 남은 스트림 닫기
	    IOUtils.closeQuietly(bufferedOutputStream);
	    IOUtils.closeQuietly(byteArrayOutputStream);		
				
	    // body에 zip파일을 담고 헤더 값 변경 후 반환
		return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"download.zip\"")
                .body(byteArrayOutputStream.toByteArray());
	}
	
	/* 첨부파일 이동 */
	public List<AttachmentDto.AttachmentResDto> moveAttachment(String attachmentId, String pdirId) {
		
		Attachment attachment = attachmentRepository.findById(Utils.StrToBigInt(attachmentId)).get(); // 이동 할 첨부파일 조회
		Pdir targetPdir = pdirRepository.findByDidAndDflagFalse(Utils.StrToBigInt(pdirId)); // 첨부파일이 이동 될 디렉토리 조회
		
		Pdir originPdir = attachment.getPdir(); // 현재 디렉토리 조회
		
		attachment.moveAttachment(targetPdir); // 첨부파일의 디렉토리 변경
		pLogService.createAttachmentLog(attachment, LogState.MOVE); // 첨부파일 이동 로그 생성
		
		return getAttachment(originPdir.getDid()); // 이동 되고 난 후 현재 디렉토리의 첨부파일 리스트 조회 후 반환
	}
	
	/* 첨부파일 복사 */
	public AttachmentDto.AttachmentResDto copyAttachment(String attachmentId, String targetPdirId) {
		
		Attachment originAttachment = attachmentRepository.findById(Utils.StrToBigInt(attachmentId)).get(); // 복사 될 첨부파일 조회
		
		Pdir targetPdir = pdirRepository.findByDidAndDflagFalse(Utils.StrToBigInt(targetPdirId)); // 첨부파일이 복사 될 디렉토리 조회
		
		Attachment copyAttachment = copyAttachmentBuilder(originAttachment, targetPdir); // 첨부파일 복사	
		
		pLogService.createAttachmentLog(copyAttachment, LogState.COPY); // 첨부파일 복사 로그 생성
		
		return attachment2AttachmentResDto(attachmentRepository.save(copyAttachment)); // 복사된 첨부파일 저장 -> DTO 변환 -> 반환
	}	
	
	/* 첨부파일 빌더 */
	private Attachment attachmentBuilder(File destinationFile, String fileName, Pdir pdir,AttachmentDto.AttachmentReqDto attachmentReqDto, MultipartFile file) {
		return Attachment.builder()
				.name(fileName)
				.url(PREFIX_URL + fileName)
				.localPath(destinationFile.getPath())
				.volume(destinationFile.length())
				.pdir(pdir)
				.coment(attachmentReqDto.getComent())
				.contentType(file.getContentType())
				.newDate(LocalDateTime.now())
				.deleteFlag(false).build();
	}
	
	/* 첨부파일 복사 빌더 */
	private Attachment copyAttachmentBuilder(Attachment originAttachment, Pdir targetPdir) {
		return Attachment.builder().pdir(targetPdir)
				.name(originAttachment.getName())
				.coment(originAttachment.getComent())
				.contentType(originAttachment.getContentType())
				.volume(originAttachment.getVolume())
				.localPath(originAttachment.getLocalPath())
				.deleteFlag(false)
				.newDate(LocalDateTime.now())
				.build();			
	}

	/* 첨부파일 서버에 저장 */
	private File saveFile(MultipartFile file) throws Exception{
				 
		UUID uid = UUID.randomUUID(); // 유니크 값 생성
		
		String fileName = uid.toString() + "_" +file.getOriginalFilename(); // 유니크한 파일 이름 생성
				
		String savedPath = calcPath(UPLOAD_PATH); //(년 월 일 기준)파일을 저장할 폴더 생성
		
		File destinationFile = new File(UPLOAD_PATH + savedPath,fileName); // 파일 객제 생성
		
		file.transferTo(destinationFile); // 파일 로컬 서버에 저장		

		return destinationFile;
	}
	

	/* Attachment 엔티티 리스트 -> Attachmnet DTO 리스트 변경 */
	private List<AttachmentDto.AttachmentResDto> attachments2AttachmentResDtos(List<Attachment> attachments) {
		List<AttachmentDto.AttachmentResDto> attachmentResDtos = new ArrayList<AttachmentDto.AttachmentResDto>();

		attachments.forEach(attachment -> {
			AttachmentDto.AttachmentResDto attachmentResDto = AttachmentDto.AttachmentResDto.builder()
					.attachmentId(attachment.getAttachmentId()).attachmentName(attachment.getName())
					.volume(attachment.getVolume()).newDate(attachment.getNewDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
					.coment(attachment.getComent())
					.contentType(attachment.getContentType()).build();

			attachmentResDtos.add(attachmentResDto);
		});

		return attachmentResDtos;
	}

	/* Attachment 엔티티 -> Attachment DTO 변경 */
	private AttachmentDto.AttachmentResDto attachment2AttachmentResDto(Attachment attachment) {

		AttachmentDto.AttachmentResDto attachmentResDto = AttachmentDto.AttachmentResDto.builder()
				.attachmentId(attachment.getAttachmentId()).attachmentName(attachment.getName())
				.volume(attachment.getVolume())
				.newDate(attachment.getNewDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
				.coment(attachment.getComent())
				.contentType(attachment.getContentType()).build();

		return attachmentResDto;
	}

	// 폴더 생성 함수
	@SuppressWarnings("unused")
	private static String calcPath(String uploadPath) {

		Calendar cal = Calendar.getInstance();

		String yearPath = File.separator + cal.get(Calendar.YEAR); // 연도 별 폴더 경로 

		String monthPath = yearPath + File.separator + new DecimalFormat("00").format(cal.get(Calendar.MONTH) + 1); // 월 별 폴더 경로 

		String datePath = monthPath + File.separator + new DecimalFormat("00").format(cal.get(Calendar.DATE)); // 일 별 폴더 경로 

		makeDir(uploadPath, yearPath, monthPath, datePath); // 폴더 생성 

		return datePath;
	}

	// 폴더 생성 함수
	private static void makeDir(String uploadPath, String... paths) {

		if (new File(uploadPath + paths[paths.length - 1]).exists()) {
			return;
		} // 해당 경로의 폴더가 존재하면 반환 

		for (String path : paths) {
			File dirPath = new File(uploadPath + path);

			if (!dirPath.exists()) {
				dirPath.mkdir();
			} // 해당 경로의 폴더 생성
		}
	}
}