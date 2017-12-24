package com.itheima.bos.service.impl;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.IDecidedzoneDao;
import com.itheima.bos.dao.INoticebillDao;
import com.itheima.bos.dao.IWorkbillDao;
import com.itheima.bos.domain.Decidedzone;
import com.itheima.bos.domain.Noticebill;
import com.itheima.bos.domain.Staff;
import com.itheima.bos.domain.User;
import com.itheima.bos.domain.Workbill;
import com.itheima.bos.service.INoticebillService;
import com.itheima.bos.utils.BOSUtils;
import com.itheima.crm.ICustomerService;

@Service("noticebillService")
@Transactional
public class NoticebillService implements INoticebillService{
	@Autowired
	private INoticebillDao noticebillDao;
	@Autowired
	private ICustomerService customerService;
	@Autowired
	private IDecidedzoneDao decidedzoneDao;
	@Autowired
	private IWorkbillDao workbillDao;
	/**
	 * ����ҵ��֪ͨ��,�����Զ��ֵ�
	 */
	@Override
	public void save(Noticebill noticebill) {
		User user = BOSUtils.getLoginUser();
		noticebill.setUser(user);
		noticebillDao.save(noticebill);
		//��ȡ�ͻ���ȡ����ַ
		String pickaddress = noticebill.getPickaddress();
		//Զ�̵���crm�Ľӿڣ�����ȡ����ַ��ѯ����id
		String decidedzoneId = customerService.findDecidedzoneIdByAddress(pickaddress);
		if(decidedzoneId!=null){
			//��ѯ���˶���id�������Զ��ֵ�
			Decidedzone decidedzone = decidedzoneDao.findById(decidedzoneId);
			Staff staff = decidedzone.getStaff();
			noticebill.setStaff(staff);//ҵ��֪ͨ������ȡ��Ա����
			//�����Զ��ֵ�
			noticebill.setOrdertype(Noticebill.ORDERTYPE_AUTO);
			//Ϊȡ��Ա����һ������
			Workbill workbill = new Workbill();
			workbill.setAttachbilltimes(0);//׷������
			workbill.setBuildtime(new Timestamp(System.currentTimeMillis()));//����ʱ�䣬��ǰϵͳʱ��
			workbill.setNoticebill(noticebill);//��������ҳ��֪ͨ��
			workbill.setPickstate(Workbill.PICKSTATE_NO);//ȡ��״̬
			workbill.setStaff(staff);//��������ȡ��Ա
			workbill.setType(Workbill.TYPE_1);//��������
			workbillDao.save(workbill);
			//����ƽ̨
			
		}
		else{
			//û�в�ѯ������id�����ܽ����Զ��ֵ����ֶ��ֵ�
			noticebill.setOrdertype(Noticebill.ORDERTYPE_MANUAL);
		}
	}

}
