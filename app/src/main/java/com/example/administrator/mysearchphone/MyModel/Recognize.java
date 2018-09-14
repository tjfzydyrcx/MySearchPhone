package com.example.administrator.mysearchphone.MyModel;

/**
 * Created by Administrator on 2017-11-30 0030.
 */
public class Recognize {

    /**
     * status : 0
     * msg : ok
     * result : {"lsprefix":"苏","lsnum":"A12345","lstype":"02","lstypename":"小型轿车","realname":"李先生","address":"浙江省杭州市拱墅区智慧立方D座603","cartype":"宝马牌7200MD","frameno":"LBVUB11080VB80000","engineno":"55665544","regdate":"2017-03-25","issuedate":"2017-03-25","usetype":"非营运"}
     */

    private String status;
    private String msg;
    private ResultBean result;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * lsprefix : 苏
         * lsnum : A12345
         * lstype : 02
         * lstypename : 小型轿车
         * realname : 李先生
         * address : 浙江省杭州市拱墅区智慧立方D座603
         * cartype : 宝马牌7200MD
         * frameno : LBVUB11080VB80000
         * engineno : 55665544
         * regdate : 2017-03-25
         * issuedate : 2017-03-25
         * usetype : 非营运
         */

        private String lsprefix;
        private String lsnum;
        private String lstype;
        private String lstypename;
        private String realname;
        private String address;
        private String cartype;
        private String frameno;
        private String engineno;
        private String regdate;
        private String issuedate;
        private String usetype;

        public String getLsprefix() {
            return lsprefix;
        }

        public void setLsprefix(String lsprefix) {
            this.lsprefix = lsprefix;
        }

        public String getLsnum() {
            return lsnum;
        }

        public void setLsnum(String lsnum) {
            this.lsnum = lsnum;
        }

        public String getLstype() {
            return lstype;
        }

        public void setLstype(String lstype) {
            this.lstype = lstype;
        }

        public String getLstypename() {
            return lstypename;
        }

        public void setLstypename(String lstypename) {
            this.lstypename = lstypename;
        }

        public String getRealname() {
            return realname;
        }

        public void setRealname(String realname) {
            this.realname = realname;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getCartype() {
            return cartype;
        }

        public void setCartype(String cartype) {
            this.cartype = cartype;
        }

        public String getFrameno() {
            return frameno;
        }

        public void setFrameno(String frameno) {
            this.frameno = frameno;
        }

        public String getEngineno() {
            return engineno;
        }

        public void setEngineno(String engineno) {
            this.engineno = engineno;
        }

        public String getRegdate() {
            return regdate;
        }

        public void setRegdate(String regdate) {
            this.regdate = regdate;
        }

        public String getIssuedate() {
            return issuedate;
        }

        public void setIssuedate(String issuedate) {
            this.issuedate = issuedate;
        }

        public String getUsetype() {
            return usetype;
        }

        public void setUsetype(String usetype) {
            this.usetype = usetype;
        }
    }
}
