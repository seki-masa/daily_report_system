package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import constants.JpaConst;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 従業員データのDTOモデル
 */

@Table(name = JpaConst.TABLE_REPGOOD)
@NamedQueries({
    @NamedQuery(
            name = JpaConst.Q_REPGOOD_GET_ALL,
            query = JpaConst.Q_REPGOOD_GET_ALL_DEF),
    @NamedQuery(
            name = JpaConst.Q_REPGOOD_COUNT,
            query = JpaConst.Q_REPGOOD_COUNT_DEF),
    @NamedQuery(
            name = JpaConst.Q_REPGOOD_GET_BY_REP_AND_EMP,
            query = JpaConst.Q_REPGOOD_GET_BY_REP_AND_EMP_DEF)

})

@Getter //全てのクラスフィールドについてgetterを自動生成する(Lombok)
@Setter //全てのクラスフィールドについてsetterを自動生成する(Lombok)
@NoArgsConstructor //引数無しコンストラクタを自動生成する(Lombok)
@AllArgsConstructor //全てのクラスフィールドを引数にもつ引数ありコンストラクタを自動生成する(Lombok)
@Entity

public class ReportGood {
    /**
     * id
     */

    @Id
    @Column(name = JpaConst.REPGOOD_COL_ID)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = JpaConst.REPGOOD_COL_EMP, nullable = false)
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = JpaConst.REPGOOD_COL_REP, nullable = false)
    private Report report;

}