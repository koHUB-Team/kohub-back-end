package kr.kohub.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import kr.kohub.dao.sql.PromotionFileInfoDaoSql;
import kr.kohub.dto.PromotionFileInfo;
import kr.kohub.type.ImageType;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class PromotionFileInfoDao {
  private NamedParameterJdbcTemplate jdbc;
  private RowMapper<PromotionFileInfo> rowMapper =
      BeanPropertyRowMapper.newInstance(PromotionFileInfo.class);
  private SimpleJdbcInsert insertAction;

  public PromotionFileInfoDao(DataSource dataSource) {
    this.jdbc = new NamedParameterJdbcTemplate(dataSource);
    this.insertAction = new SimpleJdbcInsert(dataSource).withTableName("promotion_file_info")
        .usingGeneratedKeyColumns("id").usingColumns("file_name", "save_file_name", "content_type",
            "promotion_id", "image_type_id");
  }

  public int insert(PromotionFileInfo promotionFileInfo, ImageType imageType) {
    Map<String, Object> params = new HashMap<>();
    params.put("file_name", promotionFileInfo.getFileName());
    params.put("save_file_name", promotionFileInfo.getSaveFileName());
    params.put("content_type", promotionFileInfo.getContentType());
    params.put("promotion_id", promotionFileInfo.getPromotionId());
    params.put("image_type_id", imageType.getImageTypeId());

    return insertAction.execute(params);
  }

  public PromotionFileInfo selectById(int promotionFileInfoId) {
    PromotionFileInfo promotionFileInfo;

    try {
      Map<String, Object> params = new HashMap<>();
      params.put("promotionFileInfoId", promotionFileInfoId);

      promotionFileInfo =
          jdbc.queryForObject(PromotionFileInfoDaoSql.SELECT_BY_ID, params, rowMapper);
    } catch (EmptyResultDataAccessException e) {
      promotionFileInfo = null;
    } catch (NullPointerException e) {
      promotionFileInfo = null;
    } catch (Exception e) {
      log.error(e.getMessage());
      promotionFileInfo = null;
    }

    return promotionFileInfo;
  }

  public List<PromotionFileInfo> selectByPromotionId(int promotionId) {
    List<PromotionFileInfo> promotionFileInfos;

    try {
      Map<String, Object> params = new HashMap<>();
      params.put("promotionId", promotionId);

      promotionFileInfos =
          jdbc.query(PromotionFileInfoDaoSql.SELECT_BY_PROMOTION_ID, params, rowMapper);
    } catch (EmptyResultDataAccessException e) {
      promotionFileInfos = null;
    } catch (NullPointerException e) {
      promotionFileInfos = null;
    } catch (Exception e) {
      log.error(e.getMessage());
      promotionFileInfos = null;
    }

    return promotionFileInfos;
  }

  public int deleteById(int promotionFileInfoId) {
    Map<String, Object> params = new HashMap<>();
    params.put("promotionFileInfoId", promotionFileInfoId);

    return jdbc.update(PromotionFileInfoDaoSql.DELETE_BY_ID, params);
  }
}