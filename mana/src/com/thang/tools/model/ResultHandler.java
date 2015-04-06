package com.thang.tools.model;

import java.io.Reader;
import java.nio.CharBuffer;
import java.sql.Clob;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;

import org.apache.commons.dbutils.handlers.AbstractListHandler;


public class ResultHandler extends AbstractListHandler<ResultValues> {

	@Override
	public ResultValues handleRow(ResultSet rs) throws SQLException {
		ResultSetMetaData meta=rs.getMetaData();
		int columnNum=meta.getColumnCount();
		ResultValues result=new ResultValues();
		for(int i=1;i<=columnNum;i++){
			if(Types.VARCHAR==meta.getColumnType(i)){
				result.put(meta.getColumnName(i),rs.getString(i));
			}else if(Types.INTEGER==meta.getColumnType(i)){
				result.put(meta.getColumnName(i),rs.getInt(i));
			}else if(Types.CLOB==meta.getColumnType(i)){
				Clob clob=rs.getClob(i);
				CharBuffer c=CharBuffer.allocate(1024);
				StringBuilder sber=new StringBuilder();
				int k=0;
				try{
					Reader reader=clob.getCharacterStream();
					k=reader.read(c);
				    while(-1!=k){
					    sber.append(c,0,k);
					    c.clear();
					    k=reader.read(c);
				    }
				}catch(Exception e){
					e.printStackTrace();
				}
				
				//result.put(meta.getColumnName(i), defaultLobHandler.getClobAsString(rs, i));
			}else if(Types.BLOB==meta.getColumnType(i)){
				//result.put(meta.getColumnName(i), defaultLobHandler.getBlobAsBytes(rs, i));
			}else{
				result.put(meta.getColumnName(i), rs.getObject(i));
			}
		}
		result.lowerCaseKey();//把结果数据转成小写
		return result;
	}

}
