package model;

import java.util.ArrayList;

public class ListVO {
   private ArrayList<BoardVO> list;
   private PagingBean pagingBean;
   public ListVO(ArrayList<BoardVO> list, PagingBean pagingBean) {
      super();
      this.list = list;
      this.pagingBean = pagingBean;
   }
   public ArrayList<BoardVO> getList() {
      return list;
   }
   public void setList(ArrayList<BoardVO> list) {
      this.list = list;
   }
   public PagingBean getPagingBean() {
      return pagingBean;
   }
   public void setPagingBean(PagingBean pagingBean) {
      this.pagingBean = pagingBean;
   }
   @Override
   public String toString() {
      return "ListVO [list=" + list + ", pagingBean=" + pagingBean + "]";
   }
   
}