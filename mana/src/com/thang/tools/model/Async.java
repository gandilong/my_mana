package com.thang.tools.model;

import java.io.IOException;

import javax.servlet.AsyncContext;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
/**
 * 异步处理类
 * @author zyt
 *
 */
public abstract class Async implements Runnable,AsyncListener{

		protected AsyncContext asc;
		protected ActionValues values;
		private long timeout=0;
		public Async(){
			
		}
		
		public Async(long millisecond){
			this.timeout=millisecond;
		}
		
		public Async ready(AsyncContext ac,ActionValues values){
			this.asc=ac;
			this.values=values;
			if(0==this.timeout){
				asc.setTimeout(3600);
			}else{
				asc.setTimeout(timeout);
			}
			return this;
		}
		
		/**
		 * 异步超时事件
		 */
		@Override
		public void onTimeout(AsyncEvent ae) throws IOException {
			
		}
		
		/**
		 * 异步启动事件
		 */
		@Override
		public void onStartAsync(AsyncEvent ae) throws IOException {
			
		}
		
		/**
		 * 异步出错事件
		 */
		@Override
		public void onError(AsyncEvent ae) throws IOException {
			
		}
		
		/**
		 * 异步完成事件
		 */
		@Override
		public void onComplete(AsyncEvent ae) throws IOException {
			
		}
}
