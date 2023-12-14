package services;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;

import actions.views.EmployeeConverter;
import actions.views.EmployeeView;
import actions.views.ReportConverter;
import actions.views.ReportView;
import constants.JpaConst;
import models.Employee;
import models.Report;
import models.ReportGood;

public class ReportGoodService extends ServiceBase {
    /**
     * 指定した日報にいいねした従業員データを、指定されたページ数の一覧画面に表示する分取得し、EmployeeViewのリストで返却する
     * @param report
     * @param page ページ数
     * @return 一覧画面に表示するデータのリスト
     */
    public List<EmployeeView> getAllGoodPerPage(ReportView report, int page) {
        List<Employee> employees = new ArrayList<Employee>();

        List<ReportGood> reportGood = em.createNamedQuery(JpaConst.Q_REPGOOD_GET_ALL, ReportGood.class)
                .setParameter(JpaConst.JPQL_PARM_REPORT, ReportConverter.toModel(report))
                .setFirstResult(JpaConst.ROW_PER_PAGE * (page - 1))
                .setMaxResults(JpaConst.ROW_PER_PAGE)
                .getResultList();

        for (ReportGood rg : reportGood) {
            employees.add(rg.getEmployee());
        }

        return EmployeeConverter.toViewList(employees);
    }

    /**
     * 指定した日報にいいねした従業員の件数を取得し、返却する
     * @param report
     * @return 従業員データの件数
     */
    public long countAllGood(ReportView report) {
        long count = (long) em.createNamedQuery(JpaConst.Q_REPGOOD_COUNT, Long.class)
                .setParameter(JpaConst.JPQL_PARM_REPORT, ReportConverter.toModel(report))
                .getSingleResult();
        return count;
    }

    /**
     * 画面から入力されたいいね情報を元にいいねテーブルに登録する
     * @param rv いいねされた日報の登録内容
     * @param ev いいねした従業員情報
     */
    public void create(ReportView rv, EmployeeView ev) {
        ReportGood rg = new ReportGood();
        rg.setReport(ReportConverter.toModel(rv));
        rg.setEmployee(EmployeeConverter.toModel(ev));
        createInternal(rg);
    }

    /**
     * いいねデータを1件登録する
     * @param ReportGood
     */
    public void createInternal(ReportGood rg) {
        em.getTransaction().begin();
        em.persist(rg);
        em.getTransaction().commit();
    }

    /**
     * idを条件にいいねデータを削除する
     * @param id
     */
    public void destroy(Integer id) {
        ReportGood rg = em.find(ReportGood.class, id);

        em.getTransaction().begin();
        em.remove(rg);
        em.getTransaction().commit();
        em.close();
    }

    /**
     * 指定した従業員が指定した日報にいいねしたかどうかを判別する
     * @param report
     * @param employee
     * @return rg
     */
    public ReportGood goodIdentify(ReportView rv, EmployeeView ev) {
        Employee employee = EmployeeConverter.toModel(ev);
        Report report = ReportConverter.toModel(rv);
        ReportGood rg = null;

        try {
            rg = em.createNamedQuery(JpaConst.Q_REPGOOD_GET_BY_REP_AND_EMP, ReportGood.class)
                    .setParameter(JpaConst.JPQL_PARM_REPORT, report)
                    .setParameter(JpaConst.JPQL_PARM_EMPLOYEE, employee)
                    .getSingleResult();

        } catch (NoResultException e){
            rg = null;
        }

        return rg;
    }
}

