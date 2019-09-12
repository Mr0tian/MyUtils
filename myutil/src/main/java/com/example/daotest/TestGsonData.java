package com.example.daotest;

import android.util.Log;

import com.example.daotest.bean.ListDataBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.example.daotest.util.CoordinateTransform.transformBD09ToGCJ02;
import static com.example.daotest.util.CoordinateTransform.transformGCJ02ToBD09;
import static com.example.daotest.util.CoordinateTransform.transformGCJ02ToWGS84;
import static com.example.daotest.util.CoordinateTransform.transformWGS84ToGCJ02;

/**
 * @author tian on 2019/8/30
 */
public class TestGsonData {
    public static void main(String[] args) {

       /* String jsonData = "[{\"Type\": \"Container\", \"Model\": {}, \"Children\": [{\"Type\": \"Row\", \"Model\": {}, \"Children\": []}, {\"Type\": \"Label\", \"Model\": {\"Text\": \"内容\"}, \"Children\": []}, {\"Type\": \"Label\", \"Model\": {\"Text\": \"内容\"}, \"Children\": []}]}, {\"Type\": \"Container\", \"Model\": {\"Class\": \"container-fluid\"}, \"Children\": [{\"Type\": \"TextBox\", \"Model\": {\"Id\": \"ssss\", \"Caption\": \"标题\", \"ShowLabel\": true, \"EntityAttribute\": \"OrderName\", \"AssociatedEntities\": \"xingtest02\"}, \"Children\": []}, {\"Type\": \"Label\", \"Model\": {\"Text\": \"我是lable\"}, \"Children\": []}]}, {\"Type\": \"Row\", \"Model\": {\"Class\": \"row\"}, \"Children\": [{\"Type\": \"TextBox\", \"Model\": {\"Caption\": \"rowTextbox\", \"ShowLabel\": true, \"EntityAttribute\": \"Order\", \"AssociatedEntities\": \"xingtest02\"}, \"Children\": []}, {\"Type\": \"Label\", \"Model\": {\"Text\": \"rowlable\"}, \"Children\": []}, {\"Type\": \"TextBox\", \"Model\": {\"Caption\": \"标题\", \"ShowLabel\": true, \"EntityAttribute\": \"IsDelete\", \"AssociatedEntities\": \"xingtest02\"}, \"Children\": []}]}]";
        String jsonData2 = "{\"success\":true,\"t\":{\"next\":2,\"pns\":[{\"name\":\"生产车间负责人\",\"orderNo\":1},{\"name\":\"监火人\",\"orderNo\":1},{\"name\":\"动火初审人\",\"orderNo\":1},{\"name\":\"实施安全教育人\",\"orderNo\":1},{\"name\":\"申请单位意见\",\"orderNo\":2},{\"name\":\"安全管理部门意见\",\"orderNo\":3},{\"name\":\"动火审批人意见\",\"orderNo\":4},{\"name\":\"动火前，岗位班长验票签字\",\"orderNo\":5},{\"name\":\"完工验收\",\"orderNo\":6}],\"title\":\"动火安全作业证\",\"docNo\":\"DHRLZYB19001\",\"curr\":1,\"status\":\"新建\",\"props\":{\"safety_measures_2\":\"1\",\"safety_measures_8_data_4\":\"2\",\"ah_name\":\"好好\",\"safety_measures_1\":\"1\",\"safety_measures_8_data_3\":\"2\",\"jhr_name_1\":\"好好\",\"safety_measures_4\":\"2\",\"safety_measures_8_data_2\":\"1\",\"safety_measures_3\":\"2\",\"safety_measures_8_data_1\":\"1\",\"scfzr_id\":\"39\",\"scfzr_name\":\"1\",\"sign\":\"f869aa7f1ab64428b92a0582ca288b3f.png\",\"hazard_identification_1\":\"2\",\"safety_measures_9\":\"1\",\"hazard_identification_2\":\"2\",\"ssaqjyr_id\":\"3\",\"hazard_identification_3\":\"2\",\"safety_measures_6\":\"1\",\"safety_measures_5\":\"1\",\"safety_measures_9_data\":\"1\",\"safety_measures_8\":\"1\",\"safety_measures_7\":\"1\",\"hazard_identification_8\":\"2\",\"mode\":\"r\",\"hazard_identification_9\":\"2\",\"dhr_1\":\"y\",\"hazard_identification_4\":\"2\",\"hazard_identification_5\":\"2\",\"hazard_identification_6\":\"2\",\"hazard_identification_7\":\"2\",\"dhfzr_id\":\"39\",\"dfspr_name\":\"sft-李四\",\"analysis_date_1_1\":\"r\",\"dhr_card_1\":\"j\",\"analysis_date_1_2\":\"r\",\"level\":\"1\",\"site_content\":\"r\",\"dfspr_id\":\"6\",\"analysis_time_1\":\"2019-08-29  16:51\",\"start_time\":\"2019-08-29  09:01\",\"ah_id\":\"38\",\"document_template_id\":\"1\",\"ssaqjyr_name\":\"张三\",\"jhr_id_1\":\"38\",\"analysis_name_1\":\"c\",\"dfcsr_name\":\"sft-李四\",\"safety_measures_sign_4\":\"feccdee986014eada8d39a9bce496796.png\",\"safety_measures_sign_3\":\"feccdee986014eada8d39a9bce496796.png\",\"safety_measures_sign_2\":\"feccdee986014eada8d39a9bce496796.png\",\"iv1Path\":\"ea28fdc720e34b04b579e1363a5a5f26.jpeg\",\"safety_measures_sign_1\":\"feccdee986014eada8d39a9bce496796.png\",\"dfcsr_id\":\"6\",\"hazard_identification_11\":\"2\",\"safety_measures_sign_9\":\"feccdee986014eada8d39a9bce496796.png\",\"safety_measures_sign_8\":\"feccdee986014eada8d39a9bce496796.png\",\"hazard_identification_10\":\"2\",\"safety_measures_sign_7\":\"feccdee986014eada8d39a9bce496796.png\",\"safety_measures_sign_6\":\"feccdee986014eada8d39a9bce496796.png\",\"safety_measures_sign_5\":\"feccdee986014eada8d39a9bce496796.png\",\"dwfzr_id\":\"39\",\"analysis_person_1\":\"105ce7d9dd4d4966b6e53b5952f1e535.png\",\"applicant_unit\":\"人力资源部\",\"end_time\":\"2019-08-29  16:59\",\"applicant\":\"1\",\"dhfzr_name\":\"1\",\"dwfzr_name\":\"1\",\"safety_measures_2_data\":\"1\",\"other_zy\":\"无\"}}}\n";
        Gson gson = new Gson();
        //new TypeToken<List<Object>>(){}.getType();
        TestBean testBean = gson.fromJson(jsonData2,TestBean.class);
        List<ResponseBean> list = gson.fromJson(jsonData, new TypeToken<List<Object>>(){}.getType());
        paramsData(list);
        System.out.println(list.size());*/


        double[] info =   transformBD09ToGCJ02(120.38763286253787,36.09380465634091);

     /*   System.out.println(120.384608 + 4.770000000036134E-4);

        System.out.println(36.097875 - 2.819999999985612E-4);

*/

     System.out.println(info[0]);
        System.out.println(info[1]);
    }
    //GCJ02=>WGS84   火星坐标系=>地球坐标系（精确）
    public static double[] gcj2WGSExactly(double gcjLat, double gcjLon) {
        double initDelta = 0.01;
        double threshold = 0.000000001;
        double dLat = initDelta, dLon = initDelta;
        double mLat = gcjLat - dLat, mLon = gcjLon - dLon;
        double pLat = gcjLat + dLat, pLon = gcjLon + dLon;
        double wgsLat, wgsLon, i = 0;
        while (true) {
            wgsLat = (mLat + pLat) / 2;
            wgsLon = (mLon + pLon) / 2;
            double[] tmp = transformGCJ02ToWGS84(wgsLat, wgsLon);
            dLat = tmp[0] - gcjLat;
            dLon = tmp[1] - gcjLon;
            if ((Math.abs(dLat) < threshold) && (Math.abs(dLon) < threshold))
                break;

            if (dLat > 0) pLat = wgsLat;
            else mLat = wgsLat;
            if (dLon > 0) pLon = wgsLon;
            else mLon = wgsLon;

            if (++i > 10000) break;
        }
        double[] latlon = new double[2];
        latlon[0] = wgsLat;
        latlon[1] = wgsLon;
        return latlon;
    }




    private static void paramsData(List<ResponseBean> list){

        List<ListDataBean> listDataBeans = new ArrayList<>();

        for (int i=0;i<list.size();i++){

            int rank = 0;
            //先得到数据
            ResponseBean responseBean = list.get(i);



        }
    }


    static class ResponseBean{

        /**
         * Type : Container
         * Model : {}
         * Children : [{"Type":"Row","Model":{},"Children":[]},{"Type":"Label","Model":{"Text":"内容"},"Children":[]},{"Type":"Label","Model":{"Text":"内容"},"Children":[]}]
         */

        private String Type;
        private Map<String,String> Model;
        private List<ResponseBean> Children;

        public String getType() {
            return Type;
        }

        public void setType(String type) {
            Type = type;
        }

        public Map<String, String> getModel() {
            return Model;
        }

        public void setModel(Map<String, String> model) {
            Model = model;
        }

        public void setChildren(List<ResponseBean> children) {
            Children = children;
        }
    }

    static class TestBean{



        private boolean success;
        private TBean t;

        public boolean isSuccess() {
            return success;
        }

        public void setSuccess(boolean success) {
            this.success = success;
        }

        public TBean getT() {
            return t;
        }

        public void setT(TBean t) {
            this.t = t;
        }

        public static class TBean {


            private int next;
            private String title;
            private String docNo;
            private int curr;
            private String status;

            public Map<String, String> getProps() {
                return props;
            }

            public void setProps(Map<String, String> props) {
                this.props = props;
            }

            private Map<String,String> props;
            private List<PnsBean> pns;

            public int getNext() {
                return next;
            }

            public void setNext(int next) {
                this.next = next;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getDocNo() {
                return docNo;
            }

            public void setDocNo(String docNo) {
                this.docNo = docNo;
            }

            public int getCurr() {
                return curr;
            }

            public void setCurr(int curr) {
                this.curr = curr;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }



            public List<PnsBean> getPns() {
                return pns;
            }

            public void setPns(List<PnsBean> pns) {
                this.pns = pns;
            }



            public static class PnsBean {


                private String name;
                private int orderNo;

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public int getOrderNo() {
                    return orderNo;
                }

                public void setOrderNo(int orderNo) {
                    this.orderNo = orderNo;
                }
            }
        }
    }

}
