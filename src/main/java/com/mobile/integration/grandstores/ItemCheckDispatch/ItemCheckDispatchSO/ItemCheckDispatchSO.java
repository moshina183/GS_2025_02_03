package com.mobile.integration.grandstores.ItemCheckDispatch.ItemCheckDispatchSO;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;

import com.mobile.integration.grandstores.PackageCalling.ItemCheckDispatchPkg;
import com.mobile.integration.grandstores.Utils.ResponseAPI.APIResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;


@Service
public class ItemCheckDispatchSO extends NamedParameterJdbcDaoSupport{
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Autowired
    public void setDataSource(JdbcTemplate jdbcTemplate) {
        super.setDataSource(jdbcTemplate.getDataSource());
    }
    
    @Autowired
    private ItemCheckDispatchPkg itemCheckDispatchPkg;

    private static final Logger logger = LoggerFactory.getLogger(ItemCheckDispatchSO.class);

    // GET_RTV_REQUEST_NUM
    public ResponseEntity<APIResponse> getItemCheck(Map<String, Object> content) {
    
        String p_dep_code = content.get("P_DEP_CODE")==null?"":content.get("P_DEP_CODE").toString();
        BigDecimal p_org_id = content.get("P_ORG_ID") == null ? BigDecimal.ZERO : new BigDecimal(content.get("P_ORG_ID").toString());

        Map<String, Object> ls=itemCheckDispatchPkg.callItemCheckDispatchPkg(p_dep_code, p_org_id);
        logger.info("GetItemCheckDispatch Package call response -  "+ls);    
        APIResponse api=new APIResponse();
            api.setData(ls);
            api.setStatus(HttpStatus.OK.value());    
            return ResponseEntity.ok().body(api); 
    }
}
