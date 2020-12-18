package cn.cf.common;

import java.util.Map;

import com.alibaba.fastjson.JSON;

import cn.cf.common.utils.CommonUtil;
import cn.cf.dto.B2bContractExtDto;
import cn.cf.dto.B2bOrderExtDto;
import cn.cf.entity.AddressInfo;
import cn.cf.entity.CfGoods;
import cn.cf.entity.PurchaserInfo;
import cn.cf.entity.SupplierInfo;
import cn.cf.entity.SxGoods;
import cn.cf.model.ManageAccount;

public class OrderExportUtil {

	/**
	 * 化纤订单商品导出
	 * 
	 * @param d
	 */
	public static void setExportParams(B2bOrderExtDto d) {

		if (d != null) {
			CfGoods cf = JSON.parseObject(d.getGoodsInfo(), CfGoods.class);
			if (cf != null) {
				d.setProductName(cf.getProductName());
				d.setVarietiesName(cf.getVarietiesName());
				d.setSeedvarietyName(cf.getSeedvarietyName());
				d.setSpecName(cf.getSpecName());
				d.setSeriesName(cf.getSeriesName());
				d.setBatchNumber(cf.getBatchNumber());
				d.setGradeName(cf.getGradeName());
				d.setPlantName(cf.getPlantName());

				Double packageFee = cf.getPackageFee() == null ? 0d : cf.getPackageFee();
				Double loadFee = cf.getLoadFee() == null ? 0d : cf.getLoadFee();
				Double adminFee = cf.getAdminFee() == null ? 0d : cf.getAdminFee();
				Double presentPrice = d.getPresentPrice() == null ? 0d : d.getPresentPrice();
				Double presentTotalFreight = d.getPresentTotalFreight() == null ? 0.0 : d.getPresentTotalFreight();
				Double weight = 0d;
				if (d.getAfterWeight() != null && d.getAfterWeight() == 0d) {
					weight = d.getWeight() == null ? 0d : d.getWeight();
				} else {
					weight = d.getAfterWeight() == null ? 0d : d.getAfterWeight();
				}
				Double presentTotalPrice = (presentPrice + packageFee + loadFee + adminFee) * weight
						+ presentTotalFreight;
				d.setAllPresentTotalPrice(presentTotalPrice);// 订单导出字段
			}
		}
	}

	// 纱线订单商品导出
	public static void setExportSxParams(B2bOrderExtDto d) {
		if (d != null) {
			SxGoods sx = JSON.parseObject(d.getGoodsInfo(), SxGoods.class);
			if (sx != null) {
				String technologyName = CommonUtil.isNullReturnString(sx.getTechnologyName());
				String rawMaterialParentName = CommonUtil.isNullReturnString(sx.getRawMaterialParentName());
				String rawMaterialName = CommonUtil.isNullReturnString(sx.getRawMaterialName());
				String specifications = CommonUtil.isNullReturnString(sx.getSpecName());
				String info = technologyName + " " + rawMaterialParentName + " " + " " + rawMaterialName;
				d.setProductType(info);
				Double presentTotalFreight = d.getPresentTotalFreight() == null ? 0.0 : d.getPresentTotalFreight();
				Double weight = 0d;
				if (d.getAfterWeight() != null && d.getAfterWeight() == 0d) {
					weight = d.getWeight() == null ? 0d : d.getWeight();
				} else {
					weight = d.getAfterWeight() == null ? 0d : d.getAfterWeight();
				}
				Double presentPrice = d.getPresentPrice() == null ? 0d : d.getPresentPrice();
				Double presentTotalPrice = presentPrice * weight + presentTotalFreight;
				d.setMaterialName(CommonUtil.isNullReturnString(sx.getMaterialName()));
				d.setAllPresentTotalPrice(presentTotalPrice);
			}
		}
	}

	public static void setOrderJsonInfo(B2bOrderExtDto d, ManageAccount account, String block,
			String economicsGoodsType) {
		if (d.getPurchaserInfo() != null && !"".equals(d.getPurchaserInfo())) {
			PurchaserInfo purchaserInfo = JSON.parseObject(d.getPurchaserInfo(), PurchaserInfo.class);
			d.setPurchaserPk(purchaserInfo.getPurchaserPk());
			d.setContactsTel(purchaserInfo.getContactsTel());
			String purchaserName = "";
			if (economicsGoodsType == null) {
				if (Constants.BLOCK_CF.equals(block)) {
					if (!CommonUtil.isExistAuthName(account.getPk(), ColAuthConstants.OPER_ORDERMG_COL_PURCHNAME)) {
						purchaserName = purchaserInfo.getPurchaserName() == null ? ""
								: CommonUtil.hideCompanyName(purchaserInfo.getPurchaserName());
					} else {
						purchaserName = purchaserInfo.getPurchaserName();
					}
				}

				if (Constants.BLOCK_SX.equals(block)) {
					if (!CommonUtil.isExistAuthName(account.getPk(), ColAuthConstants.YARN_ORDERMG_COL_PURCHASER)) {
						purchaserName = purchaserInfo.getPurchaserName() == null ? ""
								: CommonUtil.hideCompanyName(purchaserInfo.getPurchaserName());
					} else {
						purchaserName = purchaserInfo.getPurchaserName();
					}
				}
			} else if (Constants.BLOCK_CF.equals(block) && economicsGoodsType.equals("2")) {
				if (!CommonUtil.isExistAuthName(account.getPk(), ColAuthConstants.FC_ODER_MGR_COL_PURCHNAME)) {
					purchaserName = purchaserInfo.getPurchaserName() == null ? ""
							: CommonUtil.hideCompanyName(purchaserInfo.getPurchaserName());
				} else {
					purchaserName = purchaserInfo.getPurchaserName();
				}
			}
			d.setPurchaserName(purchaserName);
			String invoiceName = "";
			if (economicsGoodsType == null) {
				if (Constants.BLOCK_CF.equals(block)) {
					if (!CommonUtil.isExistAuthName(account.getPk(), ColAuthConstants.OPER_ORDERMG_COL_INVICENAME)) {
						invoiceName = purchaserInfo.getInvoiceName() == null ? ""
								: CommonUtil.hideCompanyName(purchaserInfo.getInvoiceName());
					} else {
						invoiceName = purchaserInfo.getInvoiceName();
					}
				}
				if (Constants.BLOCK_SX.equals(block)) {
					if (!CommonUtil.isExistAuthName(account.getPk(), ColAuthConstants.YARN_ORDERMG_COL_INVICENAME)) {
						invoiceName = purchaserInfo.getInvoiceName() == null ? ""
								: CommonUtil.hideCompanyName(purchaserInfo.getInvoiceName());
					} else {
						invoiceName = purchaserInfo.getInvoiceName();
					}
				}
			} else if (Constants.BLOCK_CF.equals(block) && economicsGoodsType.equals("2")) {
				if (!CommonUtil.isExistAuthName(account.getPk(), ColAuthConstants.FC_ODER_MGR_COL_INVICENAME)) {
					invoiceName = purchaserInfo.getInvoiceName() == null ? ""
							: CommonUtil.hideCompanyName(purchaserInfo.getInvoiceName());
				} else {
					invoiceName = purchaserInfo.getInvoiceName();
				}
			}
			d.setInvoiceName(invoiceName);
		}

		if (d.getSupplierInfo() != null && !"".equals(d.getSupplierInfo())) {
			SupplierInfo obj = JSON.parseObject(d.getSupplierInfo(), SupplierInfo.class);
			d.setSupplierPk(obj.getSupplierPk());

			String supplierName = "";
			if (economicsGoodsType == null) {
				if (Constants.BLOCK_SX.equals(block)) {
					if (!CommonUtil.isExistAuthName(account.getPk(), ColAuthConstants.YARN_ORDERMG_COL_SUPPLIER)) {
						supplierName = obj.getSupplierName() == null ? ""
								: CommonUtil.hideCompanyName(obj.getSupplierName());
					} else {
						supplierName = obj.getSupplierName();
					}

					if (!CommonUtil.isExistAuthName(account.getPk(), ColAuthConstants.OPER_ORDERMG_COL_SUPPLIER)) {
						supplierName = obj.getSupplierName() == null ? ""
								: CommonUtil.hideCompanyName(obj.getSupplierName());
					} else {
						supplierName = obj.getSupplierName();
					}
				}
			} else if (Constants.BLOCK_CF.equals(block) && economicsGoodsType.equals("2")) {
				if (!CommonUtil.isExistAuthName(account.getPk(), ColAuthConstants.FC_ODER_MGR_COL_SUPPLIER)) {
					supplierName = obj.getSupplierName() == null ? ""
							: CommonUtil.hideCompanyName(obj.getSupplierName());
				} else {
					supplierName = obj.getSupplierName();
				}
			}
			d.setSupplierName(supplierName);
		}

		if (d.getAddressInfo() != null && !"".equals(d.getAddressInfo())) {
			AddressInfo obj = JSON.parseObject(d.getAddressInfo(), AddressInfo.class);

			String provinceName = obj.getProvinceName() == null ? "" : obj.getProvinceName();
			String cityName = obj.getCityName() == null ? "" : obj.getCityName();
			String areaName = obj.getAreaName() == null ? "" : obj.getAreaName();
			String address = obj.getAddress() == null ? "" : obj.getAddress();
			String toAddress = "";
			if (economicsGoodsType == null) {
				if (Constants.BLOCK_CF.equals(block)) {
					if (!CommonUtil.isExistAuthName(account.getPk(), ColAuthConstants.OPER_ORDERMG_COL_ADDRESS)) {
						toAddress = "*****";
					} else {
						toAddress = provinceName + " " + cityName + " " + areaName + " " + address;
						;
					}
				}
				if (Constants.BLOCK_SX.equals(block)) {
					if (!CommonUtil.isExistAuthName(account.getPk(), ColAuthConstants.YARN_ORDERMG_COL_ADDRESS)) {
						toAddress = "*****";
					} else {
						toAddress = provinceName + " " + cityName + " " + areaName + " " + address;
					}
				}
			} else if (economicsGoodsType.equals("2")) {
				if(Constants.BLOCK_CF.equals(block)){
					if (!CommonUtil.isExistAuthName(account.getPk(), ColAuthConstants.FC_ODER_MGR_COL_ADDRESS)) {
						toAddress = "*****";
					} else {
						toAddress = provinceName + " " + cityName + " " + areaName + " " + address;
					}
				}
				
			}
			d.setDetailAddress(toAddress);

			String contacts = "";
			if (economicsGoodsType == null) {
				if (Constants.BLOCK_CF.equals(block)) {
					if (!CommonUtil.isExistAuthName(account.getPk(), ColAuthConstants.OPER_ORDERMG_COL_CONTACT)) {
						contacts = obj.getContacts() == null ? "" : CommonUtil.hideContacts(obj.getContacts());
					} else {
						contacts = obj.getContacts();
					}
				}
				if (Constants.BLOCK_SX.equals(block)) {
					if (!CommonUtil.isExistAuthName(account.getPk(), ColAuthConstants.YARN_ORDERMG_COL_CONTACT)) {
						contacts = obj.getContacts() == null ? "" : CommonUtil.hideContacts(obj.getContacts());
					} else {
						contacts = obj.getContacts();
					}
				}

			} else if (Constants.BLOCK_CF.equals(block) && economicsGoodsType.equals("2")) {
				if (!CommonUtil.isExistAuthName(account.getPk(), ColAuthConstants.FC_ODER_MGR_COL_CONTACT)) {
					contacts = obj.getContacts() == null ? "" : CommonUtil.hideContacts(obj.getContacts());
				} else {
					contacts = obj.getContacts();
				}
			}
			d.setContacts(contacts);

			String contactsTel = "";
			if (economicsGoodsType == null) {
				if (Constants.BLOCK_CF.equals(block)) {
					if (!CommonUtil.isExistAuthName(account.getPk(), ColAuthConstants.OPER_ORDERMG_COL_CONTACTTEL)) {
						contactsTel = obj.getContactsTel() == null ? ""
								: CommonUtil.setColRegexPhone("****", obj.getContactsTel());
					} else {
						contactsTel = obj.getContactsTel();
					}
				}
				if (Constants.BLOCK_SX.equals(block)) {
					if (!CommonUtil.isExistAuthName(account.getPk(), ColAuthConstants.YARN_ORDERMG_COL_CONTACTTEL)) {
						contactsTel = obj.getContactsTel() == null ? ""
								: CommonUtil.setColRegexPhone("****", obj.getContactsTel());
					} else {
						contactsTel = obj.getContactsTel();
					}
				}
			} else if (Constants.BLOCK_CF.equals(block) && economicsGoodsType.equals("2")) {
				if (!CommonUtil.isExistAuthName(account.getPk(), ColAuthConstants.FC_ODER_MGR_COL_CONTACTTEL)) {
					contactsTel = obj.getContactsTel() == null ? ""
							: CommonUtil.setColRegexPhone("****", obj.getContactsTel());
				} else {
					contactsTel = obj.getContactsTel();
				}
			}
			d.setContactsTel(contactsTel);
		}
	}

	public static void setMarketOrderJsonInfo(B2bOrderExtDto d, ManageAccount account) {
		if (d.getPurchaserInfo() != null && !"".equals(d.getPurchaserInfo())) {
			PurchaserInfo obj = JSON.parseObject(d.getPurchaserInfo(), PurchaserInfo.class);

			d.setPurchaserPk(obj.getPurchaserPk());

			String purchaserName = "";
			if (!CommonUtil.isExistAuthName(account.getPk(), ColAuthConstants.MARKET_ORDER_COL_PURCHASERNAME)) {
				purchaserName = obj.getPurchaserName() == null ? ""
						: CommonUtil.hideCompanyName(obj.getPurchaserName());
			} else {
				purchaserName = obj.getPurchaserName();
			}

			d.setPurchaserName(purchaserName);

			d.setContactsTel(obj.getContactsTel());
			String invoiceName = "";

			if (!CommonUtil.isExistAuthName(account.getPk(), ColAuthConstants.MARKET_ORDER_COL_INVOICENAME)) {
				invoiceName = obj.getInvoiceName() == null ? "" : CommonUtil.hideCompanyName(obj.getInvoiceName());
			} else {
				invoiceName = obj.getInvoiceName();
			}

			d.setInvoiceName(invoiceName);
		}

		if (d.getSupplierInfo() != null && !"".equals(d.getSupplierInfo())) {
			SupplierInfo obj = JSON.parseObject(d.getSupplierInfo(), SupplierInfo.class);

			d.setSupplierPk(obj.getSupplierPk());

			String supplierName = "";
			if (!CommonUtil.isExistAuthName(account.getPk(), ColAuthConstants.MARKET_ORDER_COL_SUPCOMNAME)) {
				supplierName = obj.getSupplierName() == null ? "" : CommonUtil.hideCompanyName(obj.getSupplierName());
			} else {
				supplierName = obj.getSupplierName();
			}
			d.setSupplierName(supplierName);
		}

		if (d.getAddressInfo() != null && !"".equals(d.getAddressInfo())) {
			AddressInfo obj = JSON.parseObject(d.getAddressInfo(), AddressInfo.class);
			String provinceName = obj.getProvinceName() == null ? "" : obj.getProvinceName();
			String cityName = obj.getCityName() == null ? "" : obj.getCityName();
			String areaName = obj.getAreaName() == null ? "" : obj.getAreaName();
			String address = obj.getAddress() == null ? "" : obj.getAddress();

			String toAddress = "";
			if (!CommonUtil.isExistAuthName(account.getPk(), ColAuthConstants.MARKET_ORDER_COL_ADDRESS)) {
				toAddress = "*****";
			} else {
				toAddress = provinceName + " " + cityName + " " + areaName + " " + address;
			}

			d.setDetailAddress(toAddress);
			String contacts = "";

			if (!CommonUtil.isExistAuthName(account.getPk(), ColAuthConstants.MARKET_ORDER_COL_CONTACTS)) {
				contacts = obj.getContacts() == null ? "" : CommonUtil.hideContacts(obj.getContacts());
			} else {
				contacts = obj.getContacts();
			}
			d.setContacts(contacts);

			String contactsTel = "";

			if (!CommonUtil.isExistAuthName(account.getPk(), ColAuthConstants.MARKET_CUST_PURCHASER_COL_CONTACTSTEL)) {
				contactsTel = obj.getContactsTel() == null ? "" : CommonUtil.hideContacts(obj.getContactsTel());
			} else {
				contactsTel = obj.getContactsTel();
			}
			d.setContactsTel(contactsTel);
		}
	}

	public static void setMarketOrderJsonInfoCheckMap(Map<String, String> checkMap, B2bOrderExtDto d) {
		if (d.getPurchaserInfo() != null && !"".equals(d.getPurchaserInfo())) {
			PurchaserInfo obj = JSON.parseObject(d.getPurchaserInfo(), PurchaserInfo.class);
			d.setPurchaserPk(obj.getPurchaserPk());
			String purchaserName = "";
			if (CommonUtil.isEmpty(checkMap.get(ColAuthConstants.MARKET_ORDER_COL_PURCHASERNAME))) {
				purchaserName = obj.getPurchaserName() == null ? ""
						: CommonUtil.hideCompanyName(obj.getPurchaserName());
			} else {
				purchaserName = obj.getPurchaserName();
			}
			d.setPurchaserName(purchaserName);
			d.setContactsTel(obj.getContactsTel());
			String invoiceName = "";
			if (CommonUtil.isEmpty(checkMap.get(ColAuthConstants.MARKET_ORDER_COL_INVOICENAME))) {
				invoiceName = obj.getInvoiceName() == null ? "" : CommonUtil.hideCompanyName(obj.getInvoiceName());
			} else {
				invoiceName = obj.getInvoiceName();
			}

			d.setInvoiceName(invoiceName);
		}

		if (d.getSupplierInfo() != null && !"".equals(d.getSupplierInfo())) {
			SupplierInfo obj = JSON.parseObject(d.getSupplierInfo(), SupplierInfo.class);

			d.setSupplierPk(obj.getSupplierPk());

			String supplierName = "";
			if (CommonUtil.isEmpty(checkMap.get(ColAuthConstants.MARKET_ORDER_COL_SUPCOMNAME))) {
				supplierName = obj.getSupplierName() == null ? "" : CommonUtil.hideCompanyName(obj.getSupplierName());
			} else {
				supplierName = obj.getSupplierName();
			}
			d.setSupplierName(supplierName);
		}

		if (d.getAddressInfo() != null && !"".equals(d.getAddressInfo())) {
			AddressInfo obj = JSON.parseObject(d.getAddressInfo(), AddressInfo.class);
			String provinceName = obj.getProvinceName() == null ? "" : obj.getProvinceName();
			String cityName = obj.getCityName() == null ? "" : obj.getCityName();
			String areaName = obj.getAreaName() == null ? "" : obj.getAreaName();
			String address = obj.getAddress() == null ? "" : obj.getAddress();

			String toAddress = "";
			if (CommonUtil.isEmpty(checkMap.get(ColAuthConstants.MARKET_ORDER_COL_ADDRESS))) {
				toAddress = "*****";
			} else {
				toAddress = provinceName + " " + cityName + " " + areaName + " " + address;
			}

			d.setDetailAddress(toAddress);
			String contacts = "";

			if (CommonUtil.isEmpty(checkMap.get(ColAuthConstants.MARKET_ORDER_COL_CONTACTS))) {
				contacts = obj.getContacts() == null ? "" : CommonUtil.hideContacts(obj.getContacts());
			} else {
				contacts = obj.getContacts();
			}
			d.setContacts(contacts);

			String contactsTel = "";
			if (CommonUtil.isEmpty(checkMap.get(ColAuthConstants.MARKET_CUST_PURCHASER_COL_CONTACTSTEL))) {
				contactsTel = obj.getContactsTel() == null ? "" : CommonUtil.hideContacts(obj.getContactsTel());
			} else {
				contactsTel = obj.getContactsTel();
			}
			d.setContactsTel(contactsTel);
		}
	}

	public static void setContractParams(ManageAccount account, B2bContractExtDto extDto) {

		String purchaserInfo = extDto.getPurchaserInfo();
		if (purchaserInfo != null && !"".equals(purchaserInfo)) {
			PurchaserInfo obj = JSON.parseObject(purchaserInfo, PurchaserInfo.class);
			String telephone = "";
			if (!CommonUtil.isExistAuthName(account.getPk(), ColAuthConstants.OPER_CONTRACTMG_COL_PURCHTEL)) {
				telephone = obj.getContactsTel() == null ? ""
						: CommonUtil.setColRegexPhone("****", obj.getContactsTel());
			} else {
				telephone = obj.getContactsTel();
			}
			extDto.setTelephone(telephone);

			String purchaserName = "";
			if (!CommonUtil.isExistAuthName(account.getPk(), ColAuthConstants.OPER_CONTRACTMG_COL_PURCHNAME)) {
				purchaserName = obj.getPurchaserName() == null ? ""
						: CommonUtil.hideCompanyName(obj.getPurchaserName());
			} else {
				purchaserName = obj.getPurchaserName();
			}
			extDto.setPurchaserName(purchaserName);
		}

		String addressInfo = extDto.getAddressInfo();
		if (addressInfo != null && !"".equals(addressInfo)) {
			AddressInfo obj = JSON.parseObject(addressInfo, AddressInfo.class);

			String signingCompany = "";

			if (!CommonUtil.isExistAuthName(account.getPk(), ColAuthConstants.OPER_CONTRACTMG_COL_SIGNCOMPANY)) {
				signingCompany = obj.getSigningCompany() == null ? ""
						: CommonUtil.hideCompanyName(obj.getSigningCompany());
			} else {
				signingCompany = obj.getSigningCompany() == null ? "" : obj.getSigningCompany();
			}
			extDto.setSigningCompany(signingCompany);

			String contacts = "";
			if (!CommonUtil.isExistAuthName(account.getPk(), ColAuthConstants.OPER_CONTRACTMG_COL_SIGNACC)) {
				contacts = obj.getContacts() == null ? "" : CommonUtil.hideContacts(obj.getContacts());
			} else {
				contacts = obj.getContacts();
			}
			extDto.setSigningAcc(contacts);

			String contactsTel = "";
			if (!CommonUtil.isExistAuthName(account.getPk(), ColAuthConstants.OPER_CONTRACTMG_COL_SIGNTEL)) {
				contactsTel = obj.getContactsTel() == null ? ""
						: CommonUtil.setColRegexPhone("****", obj.getContactsTel());
			} else {
				contactsTel = obj.getContactsTel();
			}
			extDto.setSigningTel(contactsTel);
			String toAddress = "";
			String provinceName = obj.getProvinceName() == null ? "" : obj.getProvinceName();
			String cityName = obj.getCityName() == null ? "" : obj.getCityName();
			String areaName = obj.getAreaName() == null ? "" : obj.getAreaName();
			String address = obj.getAddress() == null ? "" : obj.getAddress();
			if (!CommonUtil.isExistAuthName(account.getPk(), ColAuthConstants.OPER_CONTRACTMG_COL_TOADDRESS)) {
				toAddress = "*****";
			} else {
				toAddress = provinceName + " " + cityName + " " + areaName + " " + address;
			}
			extDto.setToAddress(toAddress);
		}
	}

	public static void setContractSxParams(ManageAccount account, B2bContractExtDto extDto) {

		String purchaserInfo = extDto.getPurchaserInfo();
		if (purchaserInfo != null && !"".equals(purchaserInfo)) {
			PurchaserInfo obj = JSON.parseObject(purchaserInfo, PurchaserInfo.class);
			String telephone = "";
			if (!CommonUtil.isExistAuthName(account.getPk(), ColAuthConstants.YARN_ORDER_CONTRACTMG_COL_PURCHTEL)) {
				telephone = obj.getContactsTel() == null ? ""
						: CommonUtil.setColRegexPhone("****", obj.getContactsTel());
			} else {
				telephone = obj.getContactsTel();
			}
			extDto.setTelephone(telephone);

			String purchaserName = "";
			if (!CommonUtil.isExistAuthName(account.getPk(), ColAuthConstants.YARN_ORDER_CONTRACTMG_COL_PURCHNAME)) {
				purchaserName = obj.getPurchaserName() == null ? ""
						: CommonUtil.hideCompanyName(obj.getPurchaserName());
			} else {
				purchaserName = obj.getPurchaserName();
			}
			extDto.setPurchaserName(purchaserName);
		}
		String addressInfo = extDto.getAddressInfo();
		if (addressInfo != null && !"".equals(addressInfo)) {
			AddressInfo obj = JSON.parseObject(addressInfo, AddressInfo.class);
			String signingCompany = "";
			if (!CommonUtil.isExistAuthName(account.getPk(), ColAuthConstants.YARN_ORDER_CONTRACTMG_COL_SIGNCOMPANY)) {
				signingCompany = obj.getSigningCompany() == null ? ""
						: CommonUtil.hideCompanyName(obj.getSigningCompany());
			} else {
				signingCompany = obj.getSigningCompany() == null ? "" : obj.getSigningCompany();
			}
			extDto.setSigningCompany(signingCompany);

			String contacts = "";
			if (!CommonUtil.isExistAuthName(account.getPk(), ColAuthConstants.YARN_ORDER_CONTRACTMG_COL_SIGNACC)) {
				contacts = obj.getContacts() == null ? "" : CommonUtil.hideContacts(obj.getContacts());
			} else {
				contacts = obj.getContacts();
			}
			extDto.setSigningAcc(contacts);

			String contactsTel = "";
			if (!CommonUtil.isExistAuthName(account.getPk(), ColAuthConstants.YARN_ORDER_CONTRACTMG_COL_SIGNTEL)) {
				contactsTel = obj.getContactsTel() == null ? ""
						: CommonUtil.setColRegexPhone("****", obj.getContactsTel());
			} else {
				contactsTel = obj.getContactsTel();
			}
			extDto.setSigningTel(contactsTel);

			String toAddress = "";
			String provinceName = obj.getProvinceName() == null ? "" : obj.getProvinceName();
			String cityName = obj.getCityName() == null ? "" : obj.getCityName();
			String areaName = obj.getAreaName() == null ? "" : obj.getAreaName();
			String address = obj.getAddress() == null ? "" : obj.getAddress();
			if (!CommonUtil.isExistAuthName(account.getPk(), ColAuthConstants.YARN_ORDER_CONTRACTMG_COL_TOADDRESS)) {
				toAddress = "*****";
			} else {
				toAddress = provinceName + " " + cityName + " " + areaName + " " + address;
			}
			extDto.setToAddress(toAddress);
		}
	}

	/**
	 * 判断权限，先把用户权限数据存入Map，根据取出map是否有值，判断是否有权限
	 * 
	 * @param d
	 * @param checkMap
	 * @param block
	 * @param economicsGoodsType
	 */
	public static void setOrderJsonInfoCheckMap(B2bOrderExtDto d, Map<String, String> checkMap, String block,
			String economicsGoodsType) {
		if (CommonUtil.isNotEmpty(d.getPurchaserInfo())) {
			PurchaserInfo purchaserInfo = JSON.parseObject(d.getPurchaserInfo(), PurchaserInfo.class);
			d.setPurchaserPk(purchaserInfo.getPurchaserPk());
			String purchaserName = "";
			if (economicsGoodsType == null) {
				if (Constants.BLOCK_CF.equals(block)) {
					if (CommonUtil.isEmpty(checkMap.get(ColAuthConstants.OPER_ORDERMG_COL_PURCHNAME))) {
						purchaserName = purchaserInfo.getPurchaserName() == null ? ""
								: CommonUtil.hideCompanyName(purchaserInfo.getPurchaserName());
					} else {
						purchaserName = CommonUtil.isNullReturnString(purchaserInfo.getPurchaserName());
					}
				}
				if (Constants.BLOCK_SX.equals(block)) {
					if (CommonUtil.isEmpty(checkMap.get(ColAuthConstants.YARN_ORDERMG_COL_PURCHASER))) {
						purchaserName = purchaserInfo.getPurchaserName() == null ? ""
								: CommonUtil.hideCompanyName(purchaserInfo.getPurchaserName());
					} else {
						purchaserName = CommonUtil.isNullReturnString(purchaserInfo.getPurchaserName());
					}
				}
			} else if (economicsGoodsType.equals("2") && Constants.BLOCK_CF.equals(block)) {
				if (CommonUtil.isEmpty(checkMap.get(ColAuthConstants.FC_ODER_MGR_COL_PURCHNAME))) {
					purchaserName = purchaserInfo.getPurchaserName() == null ? ""
							: CommonUtil.hideCompanyName(purchaserInfo.getPurchaserName());
				} else {
					purchaserName = CommonUtil.isNullReturnString(purchaserInfo.getPurchaserName());
				}
			}

			d.setPurchaserName(purchaserName);
			String invoiceName = "";
			if (economicsGoodsType == null) {
				if (Constants.BLOCK_CF.equals(block)) {
					if (CommonUtil.isEmpty(checkMap.get(ColAuthConstants.OPER_ORDERMG_COL_INVICENAME))) {
						invoiceName = purchaserInfo.getInvoiceName() == null ? ""
								: CommonUtil.hideCompanyName(purchaserInfo.getInvoiceName());
					} else {
						invoiceName = purchaserInfo.getInvoiceName();
					}
				}
				if (Constants.BLOCK_SX.equals(block)) {
					if (CommonUtil.isEmpty(checkMap.get(ColAuthConstants.YARN_ORDERMG_COL_INVICENAME))) {
						invoiceName = purchaserInfo.getInvoiceName() == null ? ""
								: CommonUtil.hideCompanyName(purchaserInfo.getInvoiceName());
					} else {
						invoiceName = CommonUtil.isNullReturnString(purchaserInfo.getInvoiceName());
					}
				}
			} else if (economicsGoodsType.equals("2") && Constants.BLOCK_CF.equals(block)) {
				if (CommonUtil.isEmpty(checkMap.get(ColAuthConstants.FC_ODER_MGR_COL_INVICENAME))) {
					invoiceName = purchaserInfo.getInvoiceName() == null ? ""
							: CommonUtil.hideCompanyName(purchaserInfo.getInvoiceName());
				} else {
					invoiceName = purchaserInfo.getInvoiceName();
				}

			}
			d.setInvoiceName(invoiceName);
		}

		if (CommonUtil.isNotEmpty((d.getSupplierInfo()))) {
			SupplierInfo obj = JSON.parseObject(d.getSupplierInfo(), SupplierInfo.class);
			d.setSupplierPk(obj.getSupplierPk());

			String supplierName = "";
			if (economicsGoodsType == null) {
				if (Constants.BLOCK_CF.equals(block)) {
					if (CommonUtil.isEmpty(checkMap.get(ColAuthConstants.OPER_ORDERMG_COL_SUPPLIER))) {
						supplierName = obj.getSupplierName() == null ? ""
								: CommonUtil.hideCompanyName(obj.getSupplierName());
					} else {
						supplierName = CommonUtil.isNullReturnString(obj.getSupplierName());
					}
				}
				if (Constants.BLOCK_SX.equals(block)) {
					if (CommonUtil.isEmpty(checkMap.get(ColAuthConstants.YARN_ORDERMG_COL_SUPPLIER))) {
						supplierName = obj.getSupplierName() == null ? ""
								: CommonUtil.hideCompanyName(obj.getSupplierName());
					} else {
						supplierName = CommonUtil.isNullReturnString(obj.getSupplierName());
					}
				}
			} else if (economicsGoodsType.equals("2") && Constants.BLOCK_CF.equals(block)) {
				if (CommonUtil.isEmpty(checkMap.get(ColAuthConstants.FC_ODER_MGR_COL_SUPPLIER))) {
					supplierName = obj.getSupplierName() == null ? ""
							: CommonUtil.hideCompanyName(obj.getSupplierName());
				} else {
					supplierName = CommonUtil.isNullReturnString(obj.getSupplierName());
				}
			}
			d.setSupplierName(supplierName);
		}

		if (CommonUtil.isNotEmpty((d.getAddressInfo()))) {
			AddressInfo obj = JSON.parseObject(d.getAddressInfo(), AddressInfo.class);

			String provinceName = obj.getProvinceName() == null ? "" : obj.getProvinceName();
			String cityName = obj.getCityName() == null ? "" : obj.getCityName();
			String areaName = obj.getAreaName() == null ? "" : obj.getAreaName();
			String address = obj.getAddress() == null ? "" : obj.getAddress();
			String toAddress = "";
			if (economicsGoodsType == null) {
				if (Constants.BLOCK_CF.equals(block)) {
					if (CommonUtil.isEmpty(checkMap.get(ColAuthConstants.OPER_ORDERMG_COL_ADDRESS))) {
						toAddress = "*****";
					} else {
						toAddress = provinceName + " " + cityName + " " + areaName + " " + address;
						;
					}
				}
				if (Constants.BLOCK_SX.equals(block)) {
					if (CommonUtil.isEmpty(checkMap.get(ColAuthConstants.YARN_ORDERMG_COL_ADDRESS))) {
						toAddress = "*****";
					} else {
						toAddress = provinceName + " " + cityName + " " + areaName + " " + address;
					}
				}
			} else if (economicsGoodsType.equals("2") && Constants.BLOCK_CF.equals(block)) {
				if (CommonUtil.isEmpty(checkMap.get(ColAuthConstants.FC_ODER_MGR_COL_ADDRESS))) {
					toAddress = "*****";
				} else {
					toAddress = provinceName + " " + cityName + " " + areaName + " " + address;
				}
			}
			d.setDetailAddress(toAddress);

			String contacts = "";
			if (economicsGoodsType == null) {
				if (Constants.BLOCK_CF.equals(block)) {
					if (CommonUtil.isEmpty(checkMap.get(ColAuthConstants.OPER_ORDERMG_COL_CONTACT))) {
						contacts = obj.getContacts() == null ? "" : CommonUtil.hideContacts(obj.getContacts());
					} else {
						contacts = CommonUtil.isNullReturnString(obj.getContacts());
					}
				}
				if (Constants.BLOCK_SX.equals(block)) {
					if (CommonUtil.isEmpty(checkMap.get(ColAuthConstants.YARN_ORDERMG_COL_CONTACT))) {
						contacts = obj.getContacts() == null ? "" : CommonUtil.hideContacts(obj.getContacts());
					} else {
						contacts = CommonUtil.isNullReturnString(obj.getContacts());
					}
				}
			} else if (economicsGoodsType.equals("2") && Constants.BLOCK_CF.equals(block)) {
				if (CommonUtil.isEmpty(checkMap.get(ColAuthConstants.FC_ODER_MGR_COL_CONTACT))) {
					contacts = obj.getContacts() == null ? "" : CommonUtil.hideContacts(obj.getContacts());
				} else {
					contacts = CommonUtil.isNullReturnString(obj.getContacts());
				}
			}
			d.setContacts(contacts);

			String contactsTel = "";
			if (economicsGoodsType == null) {
				if (Constants.BLOCK_CF.equals(block)) {
					if (CommonUtil.isEmpty(checkMap.get(ColAuthConstants.OPER_ORDERMG_COL_CONTACTTEL))) {
						contactsTel = obj.getContactsTel() == null ? ""
								: CommonUtil.setColRegexPhone("****", obj.getContactsTel());
					} else {
						contactsTel = CommonUtil.isNullReturnString(obj.getContactsTel());
					}
				}
				if (Constants.BLOCK_SX.equals(block)) {
					if (CommonUtil.isEmpty(checkMap.get(ColAuthConstants.YARN_ORDERMG_COL_CONTACTTEL))) {
						contactsTel = obj.getContactsTel() == null ? ""
								: CommonUtil.setColRegexPhone("****", obj.getContactsTel());
					} else {
						contactsTel = CommonUtil.isNullReturnString(obj.getContactsTel());
					}
				}
			} else if (economicsGoodsType.equals("2") && Constants.BLOCK_CF.equals(block)) {
				if (CommonUtil.isEmpty(checkMap.get(ColAuthConstants.FC_ODER_MGR_COL_CONTACTTEL))) {
					contactsTel = obj.getContactsTel() == null ? ""
							: CommonUtil.setColRegexPhone("****", obj.getContactsTel());
				} else {
					contactsTel = CommonUtil.isNullReturnString(obj.getContactsTel());
				}
			}
			d.setContactsTel(contactsTel);
		}

		String goodsInfo = d.getGoodsInfo();
		if (CommonUtil.isNotEmpty(goodsInfo)) {
			String plantName = "";
			if (economicsGoodsType == null) {
				if (Constants.BLOCK_CF.equals(block)) {
					CfGoods cfGoods = JSON.parseObject(goodsInfo, CfGoods.class);
					if (CommonUtil.isEmpty(checkMap.get(ColAuthConstants.OPER_ORDERMG_COL_PLANT))) {
						plantName = cfGoods.getPlantName() == null ? ""
								: CommonUtil.hideCompanyName(cfGoods.getPlantName());
					} else {
						plantName = CommonUtil.isNullReturnString(cfGoods.getPlantName());
					}
				}
				if (Constants.BLOCK_SX.equals(block)) {
					SxGoods sxGoods = JSON.parseObject(goodsInfo, SxGoods.class);
					if (CommonUtil.isEmpty(checkMap.get(ColAuthConstants.YARN_ORDERMG_COL_PLANT))) {
						plantName = sxGoods.getPlantName() == null ? ""
								: CommonUtil.hideCompanyName(sxGoods.getPlantName());
					} else {
						plantName = CommonUtil.isNullReturnString(sxGoods.getPlantName());
					}
				}
			} else if (economicsGoodsType.equals("2") && Constants.BLOCK_CF.equals(block)) {
				CfGoods cfGoods = JSON.parseObject(goodsInfo, CfGoods.class);
				if (CommonUtil.isEmpty(checkMap.get(ColAuthConstants.OPER_ORDERMG_COL_PLANT))) {
					plantName = cfGoods.getPlantName() == null ? ""
							: CommonUtil.hideCompanyName(cfGoods.getPlantName());
				} else {
					plantName = CommonUtil.isNullReturnString(cfGoods.getPlantName());
				}
			}
			d.setPlantName(plantName);
		}
	}

	public static void setMarketOrderJsonInfoCheckMap(B2bOrderExtDto d, Map<String, String> checkMap) {
		if (d.getPurchaserInfo() != null && !"".equals(d.getPurchaserInfo())) {
			PurchaserInfo obj = JSON.parseObject(d.getPurchaserInfo(), PurchaserInfo.class);

			d.setPurchaserPk(obj.getPurchaserPk());
			String purchaserName = "";
			if (CommonUtil.isEmpty(checkMap.get(ColAuthConstants.MARKET_ORDER_COL_PURCHASERNAME))) {
				purchaserName = obj.getPurchaserName() == null ? ""
						: CommonUtil.hideCompanyName(obj.getPurchaserName());
			} else {
				purchaserName = obj.getPurchaserName();
			}

			d.setPurchaserName(purchaserName);

			String contactsTel = "";

			if (CommonUtil.isEmpty(checkMap.get(ColAuthConstants.MARKET_ORDER_COL_CONTACTSTEL))) {
				contactsTel = obj.getContactsTel() == null ? "" : CommonUtil.hideContactTel(obj.getContactsTel());
			} else {
				contactsTel = obj.getContactsTel();
			}
			d.setContactsTel(contactsTel);

			String invoiceName = "";

			if (CommonUtil.isEmpty(checkMap.get(ColAuthConstants.MARKET_ORDER_COL_INVOICENAME))) {
				invoiceName = obj.getInvoiceName() == null ? "" : CommonUtil.hideCompanyName(obj.getInvoiceName());
			} else {
				invoiceName = obj.getInvoiceName();
			}

			d.setInvoiceName(invoiceName);
		}

		if (d.getSupplierInfo() != null && !"".equals(d.getSupplierInfo())) {
			SupplierInfo obj = JSON.parseObject(d.getSupplierInfo(), SupplierInfo.class);

			d.setSupplierPk(obj.getSupplierPk());

			String supplierName = "";
			if (CommonUtil.isEmpty(checkMap.get(ColAuthConstants.MARKET_ORDER_COL_SUPCOMNAME))) {
				supplierName = obj.getSupplierName() == null ? "" : CommonUtil.hideCompanyName(obj.getSupplierName());
			} else {
				supplierName = obj.getSupplierName();
			}
			d.setSupplierName(supplierName);
		}

		if (d.getAddressInfo() != null && !"".equals(d.getAddressInfo())) {
			AddressInfo obj = JSON.parseObject(d.getAddressInfo(), AddressInfo.class);
			String provinceName = obj.getProvinceName() == null ? "" : obj.getProvinceName();
			String cityName = obj.getCityName() == null ? "" : obj.getCityName();
			String areaName = obj.getAreaName() == null ? "" : obj.getAreaName();
			String address = obj.getAddress() == null ? "" : obj.getAddress();

			String toAddress = "";
			if (CommonUtil.isEmpty(checkMap.get(ColAuthConstants.MARKET_ORDER_COL_ADDRESS))) {
				toAddress = "*****";
			} else {
				toAddress = provinceName + " " + cityName + " " + areaName + " " + address;
			}

			d.setDetailAddress(toAddress);
			String contacts = "";

			if (CommonUtil.isEmpty(checkMap.get(ColAuthConstants.MARKET_ORDER_COL_CONTACTS))) {
				contacts = obj.getContacts() == null ? "" : CommonUtil.hideContacts(obj.getContacts());
			} else {
				contacts = obj.getContacts();
			}
			d.setContacts(contacts);

			String contactsTel = "";

			if (CommonUtil.isEmpty(checkMap.get(ColAuthConstants.MARKET_ORDER_COL_CONTACTSTEL))) {
				contactsTel = obj.getContactsTel() == null ? "" : CommonUtil.hideContactTel(obj.getContactsTel());
			} else {
				contactsTel = obj.getContactsTel();
			}
			d.setContactsTel(contactsTel);
		}
	}

	public static void setOperContractParams(ManageAccount account, B2bContractExtDto extDto) {

		String purchaserInfo = extDto.getPurchaserInfo();
		if (purchaserInfo != null && !"".equals(purchaserInfo)) {
			PurchaserInfo obj = JSON.parseObject(purchaserInfo, PurchaserInfo.class);
			String telephone = "";
			if (!CommonUtil.isExistAuthName(account.getPk(), ColAuthConstants.MARKET_CONTRACT_MANAGE_COL_PURCHTEL)) {
				telephone = obj.getContactsTel() == null ? ""
						: CommonUtil.setColRegexPhone("****", obj.getContactsTel());
			} else {
				telephone = obj.getContactsTel();
			}
			extDto.setTelephone(telephone);

			String purchaserName = "";
			if (!CommonUtil.isExistAuthName(account.getPk(), ColAuthConstants.MARKET_CONTRACT_MANAGE_COL_PURCHNAME)) {
				purchaserName = obj.getPurchaserName() == null ? ""
						: CommonUtil.hideCompanyName(obj.getPurchaserName());
			} else {
				purchaserName = obj.getPurchaserName();
			}
			extDto.setPurchaserName(purchaserName);
		}
		String addressInfo = extDto.getAddressInfo();
		if (addressInfo != null && !"".equals(addressInfo)) {
			AddressInfo obj = JSON.parseObject(addressInfo, AddressInfo.class);
			String signingCompany = "";
			if (!CommonUtil.isExistAuthName(account.getPk(), ColAuthConstants.MARKET_CONTRACT_MANAGE_COL_SIGNCOMPANY)) {
				signingCompany = obj.getSigningCompany() == null ? ""
						: CommonUtil.hideCompanyName(obj.getSigningCompany());
			} else {
				signingCompany = obj.getSigningCompany() == null ? "" : obj.getSigningCompany();
			}
			extDto.setSigningCompany(signingCompany);

			String contacts = "";
			if (!CommonUtil.isExistAuthName(account.getPk(), ColAuthConstants.MARKET_CONTRACT_MANAGE_COL_SIGNACC)) {
				contacts = obj.getContacts() == null ? "" : CommonUtil.hideContacts(obj.getContacts());
			} else {
				contacts = obj.getContacts();
			}
			extDto.setSigningAcc(contacts);

			String contactsTel = "";
			if (!CommonUtil.isExistAuthName(account.getPk(), ColAuthConstants.MARKET_CONTRACT_MANAGE_COL_SIGNTEL)) {
				contactsTel = obj.getContactsTel() == null ? ""
						: CommonUtil.setColRegexPhone("****", obj.getContactsTel());
			} else {
				contactsTel = obj.getContactsTel();
			}
			extDto.setSigningTel(contactsTel);

			String toAddress = "";
			String provinceName = obj.getProvinceName() == null ? "" : obj.getProvinceName();
			String cityName = obj.getCityName() == null ? "" : obj.getCityName();
			String areaName = obj.getAreaName() == null ? "" : obj.getAreaName();
			String townName = obj.getTownName() == null ? "" : obj.getTownName();
			String address = obj.getAddress() == null ? "" : obj.getAddress();
			if (!CommonUtil.isExistAuthName(account.getPk(), ColAuthConstants.MARKET_CONTRACT_MANAGE_COL_TOADDRESS)) {
				toAddress = "*****";
			} else {
				toAddress = provinceName + " " + cityName + " " + areaName + " " + townName + " " + address;
			}
			extDto.setToAddress(toAddress);
		}
	}

}
