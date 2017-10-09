/*    */ package org.szcloud.framework.core.domain;
/*    */ 
/*    */ public class QueryException extends RuntimeException
/*    */ {
/*    */   private static final long serialVersionUID = -6280573107268515266L;
/*    */ 
/*    */   public QueryException()
/*    */   {
/*    */   }
/*    */ 
/*    */   public QueryException(String message)
/*    */   {
/* 11 */     super(message);
/*    */   }
/*    */ 
/*    */   public QueryException(Throwable cause) {
/* 15 */     super(cause);
/*    */   }
/*    */ 
/*    */   public QueryException(String message, Throwable cause) {
/* 19 */     super(message, cause);
/*    */   }
/*    */ }

