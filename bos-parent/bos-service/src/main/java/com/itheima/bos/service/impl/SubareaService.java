package com.itheima.bos.service.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.ISubareaDao;
import com.itheima.bos.domain.Subarea;
import com.itheima.bos.service.ISubareaService;
import com.itheima.bos.utils.PageBean;

@Service("subareaService")
@Transactional
public class SubareaService implements ISubareaService{

	@Autowired
	private ISubareaDao subareaDao;
	
	/**
	 * ��������
	 */
	@Override
	public void save(Subarea model) {
		subareaDao.save(model);
	}

	/**
	 * ��ҳ��ѯ
	 */
	@Override
	public void pageQuery(PageBean pageBean) {
		// TODO Auto-generated method stub
		subareaDao.pageQuery(pageBean);
	}

	/**
	 * ��ȡ���еķ���
	 */
	@Override
	public List<Subarea> findAll() {
		// TODO Auto-generated method stub
		return subareaDao.findAll();
	}

	/**
	 * ��ȡ����δ�����ķ���
	 */
	@Override
	public List<Subarea> findByNotAssociation() {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Subarea.class);
		//��ӹ�������������������decidedzone����δnull
		detachedCriteria.add(Restrictions.isNull("decidedzone"));
		return subareaDao.findByCriteria(detachedCriteria);
	}

	/**
	 * ���ݶ���id��ȡ����list
	 */
	@Override
	public List<Subarea> findListByDecidedzoneId(String decidedzoneId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Subarea.class);
		//��ӹ�������,ע�⣺���ʱjoin���ű��ʱ����Ҫ�õ�����
		//�����Ҫ����join���������������е����ݣ���ô����Ҫ��ӱ�ı���
		//����id�������ڷ����У����Դ�ʱ����Ҫjoin���ӣ�����õ�����name��ѯ����ô����Ҫʹ�ñ�����
		criteria.add(Restrictions.eq("decidedzone.id", decidedzoneId));
		List<Subarea> list = subareaDao.findByCriteria(criteria);
		return list;
	}

	/**
	 * ��ȡ��������ֲ�����
	 */
	@Override
	public List<Object> findSubareaGroupByProvince() {
		List<Object> list = subareaDao.findSubareaGroupByProvince();
		return list;
	}
	
}
