package com.dy.store.attach.service;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.dy.store.attach.domain.AttachDto;
import com.dy.store.attach.mapper.AttachMapper;
import com.dy.store.common.util.AttachUtils;
import com.dy.store.common.util.CommonUtils;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class AttachServiceImpl implements AttachService {

	private AttachMapper attachMapper;

	/**
	 * 단일 파일 삭제
	 * 
	 * @param id - PK
	 * @param code - 상품 코드
	 * @return boolean
	 */
	@Override
	public boolean deleteAttach(Long id, String code) {

		/* 파일 정보 조회 */
		AttachDto attach = attachMapper.selectAttachDetail(id);
		if (attach == null) {
			return false;
		}

		/* 파일 정보 삭제 */
		int queryResult = attachMapper.deleteAttach(attach);
		if (queryResult != 1) {
			return false;
		}

		/* 업로드한 날짜 */
		String uploadedDate = CommonUtils.formatDate(attach.getCreatedDate(), "yy-MM-dd");

		/* 디스크에서 파일 삭제 */
		boolean isDeleted = AttachUtils.deleteFile(attach.getStoredName(), uploadedDate);
		if (isDeleted == false) {
			return false;
		}

		return true;
	}

	/**
	 * 전체 파일 삭제
	 * 
	 * @param code - 상품 코드
	 * @return boolean
	 */
	@Override
	public boolean deleteAttach(String code) {

		/* 파일 목록 조회 */
		List<AttachDto> attachList = attachMapper.selectAttachList(code);
		if (CollectionUtils.isEmpty(attachList) == true) {
			return false;
		}

		/* 파일 정보 삭제 */
		int queryResult = attachMapper.deleteAttach(AttachDto.builder().code(code).build());
		if (queryResult != 1) {
			return false;
		}

		for (AttachDto attach : attachList) {
			/* 업로드한 날짜 */
			String uploadedDate = CommonUtils.formatDate(attach.getCreatedDate(), "yy-MM-dd");

			/* 디스크에서 파일 삭제 */
			boolean isDeleted = AttachUtils.deleteFile(attach.getStoredName(), uploadedDate);
			if (isDeleted == false) {
				return false;
			}
		}

		return true;
	}

	/**
	 * 파일 목록 조회
	 * 
	 * @param code - 상품 코드
	 * @return 파일 목록
	 */
	@Override
	public List<AttachDto> getAttachList(String code) {

		/* 파일 목록을 담을 비어있는 리스트 */
		List<AttachDto> attachList = Collections.emptyList();

		/* 전체 파일 수 */
		int attachTotalCount = attachMapper.selectAttachTotalCount(code);
		if (attachTotalCount > 0) {
			attachList = attachMapper.selectAttachList(code);
		}

		return attachList;
	}

}
