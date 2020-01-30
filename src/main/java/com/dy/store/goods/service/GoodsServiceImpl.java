package com.dy.store.goods.service;

import java.util.Collections;
import java.util.List;

import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import com.dy.store.attach.domain.AttachDto;
import com.dy.store.attach.mapper.AttachMapper;
import com.dy.store.common.paging.PaginationInfo;
import com.dy.store.common.util.AttachUtils;
import com.dy.store.goods.domain.GoodsDto;
import com.dy.store.goods.mapper.GoodsMapper;
import com.dy.store.stock.domain.StockDto;
import com.dy.store.stock.mapper.StockMapper;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class GoodsServiceImpl implements GoodsService {

	private GoodsMapper goodsMapper;

	private StockMapper stockMapper;

	private AttachMapper attachMapper;

	/**
	 * 상품 등록 (Insert or Update)
	 * 
	 * @param params - GoodsDto
	 * @return boolean
	 */
	@Override
	public boolean registerGoods(GoodsDto params) {

		int queryResult = 0; /* Insert, Update 쿼리 실행 결과 */

		/* Insert (파라미터에 PK가 존재하지 않는 경우) */
		if (Strings.isBlank(params.getCode()) == true) {
			/* 상품 정보 등록 */
			queryResult = goodsMapper.insertGoods(params);
			if (queryResult != 1) {
				return false;
			}

			/* 재고 정보 파라미터에 상품 코드 저장 */
			params.getStock().setCode(params.getCode());
			/* 재고 정보 등록 */
			queryResult = stockMapper.insertStock(params.getStock());
			if (queryResult != 1) {
				return false;
			}

			/* Update (파라미터에 PK가 존재하는 경우) */
		} else {
			/* 상품 수정 */
			queryResult = goodsMapper.updateGoods(params);
			if (queryResult != 1) {
				return false;
			}

			/* 재고 수정 */
			params.getStock().setCode(params.getCode());
			queryResult = stockMapper.updateStock(params.getStock());
			if (queryResult != 1) {
				return false;
			}
		}

		return true;
	}

	/**
	 * 상품 등록 (Insert or Update)
	 * 
	 * @param params - GoodsDto
	 * @param files - 상품 이미지 파일
	 * @return boolean
	 */
	@Override
	public boolean registerGoods(GoodsDto params, MultipartFile[] files) {

		boolean isRegistered = registerGoods(params);
		if (isRegistered == false) {
			return false;
		}

		/* 파일 업로드 */
		List<AttachDto> attachList = AttachUtils.uploadFiles(files, params.getCode());
		if (CollectionUtils.isEmpty(attachList) == true) {
			return false;
		}

		int queryResult = 0;

		/* 이미지 정보 등록 */
		for (AttachDto attach : attachList) {
			queryResult += attachMapper.insertAttach(attach);
		}
		if (queryResult < 1) {
			return false;
		}

		return true;
	}

	/**
	 * 상품 정보 조회
	 * 
	 * @param code - 상품 코드
	 * @return 상품 정보
	 */
	@Override
	public GoodsDto getGoodsDetail(String code) {

		GoodsDto goods = goodsMapper.selectGoodsDetail(code);
		if (goods != null) {
			StockDto stock = stockMapper.selectStockDetail(code);
			goods.setStock(stock);
		}

		return goods;
	}

	/**
	 * 상품 삭제
	 * 
	 * @param codeList - 상품 코드 목록
	 * @return boolean
	 */
	@Override
	public boolean deleteGoods(List<String> codeList) {

		int queryResult = goodsMapper.deleteGoods(codeList);
		if (queryResult < 1) {
			return false;
		}

		return true;
	}

	/**
	 * 상품 목록 조회
	 * 
	 * @params - GoodsDto
	 * @return 상품 목록
	 */
	@Override
	public List<GoodsDto> getGoodsList(GoodsDto params) {

		/* 상품 목록을 담을 비어있는 리스트 */
		List<GoodsDto> goodsList = Collections.emptyList();
		/* 전체 상품 수 */
		int goodsTotalCount = goodsMapper.selectGoodsTotalCount(params);

		/* 페이징 계산에 필요한 전체 회원 수 저장 */
		params.setTotalRecordCount(goodsTotalCount);
		/* Pagination 클래스에서 페이징 정보를 계산하고, 계산이 완료된 paginationInfo를 params에 저장 */
		params.setPaginationInfo(new PaginationInfo(params));

		if (goodsTotalCount > 0) {
			/* 상품 목록 조회 */
			goodsList = goodsMapper.selectGoodsList(params);
		}

		return goodsList;
	}

}
