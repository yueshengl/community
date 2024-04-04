package com.nowcoder.community.entity;

/**
 * @Author: Dai
 * @Date: 2024/03/25 13:28
 * @Description: 封装分页相关的信息
 * @Version: 1.0
 */

public class Page {
    // 当前页码
    private int current = 1;
    // 显示上限
    private int limit = 10;
    // 数据总数(用于计算总页数)
    private int rows;
    // 查询路径(用于复用分页链接)
    private String path;

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        if(current >= 1){
            this.current = current;
        }
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        if(limit >= 1 && limit <= 100){
            this.limit = limit;
        }
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        if(rows >= 0){
            this.rows = rows;
        }
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    /**
     * 获取当前页的偏移量。
     * 该方法用于计算当前页的起始位置，基于当前页码和每页的限制条数。
     *
     * @return int 返回当前页的偏移量。计算方式为：(当前页码 - 1) * 每页限制的条数。
     */
    public int getOffset(){
        // current * limit - limit
        return (current - 1) * limit;
    }

    /**
     * 计算总页数
     * 该方法不接受参数，根据已知的记录总数（rows）和每页的记录数（limit）来计算总页数。
     * 如果记录总数正好能被每页记录数整除，则返回记录总数除以每页记录数的商；
     * 如果记录总数不能被每页记录数整除，则返回记录总数除以每页记录数的商加一。
     *
     * @return int 返回计算出的总页数。
     */
    public int getTotal(){
        if(rows % limit == 0){
            return rows / limit;
        }else{
            return rows / limit + 1;
        }
    }


    /**
     * 获取起始页码。
     *
     * @return 返回一个整数类型的值。
     */
    public int getFrom(){
        int from = current - 2;
        return from < 1 ? 1 : from;
    }


    /**
     * 获取结束页码。
     *
     * @return 返回一个整数类型的值。
     */
    public int getTo(){
        int to = current + 2;
        int total = getTotal();
        return to > total ? total : to;
    }


}
