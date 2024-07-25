package org.example.jakartasample.transaction;

import jakarta.annotation.Resource;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import org.example.jakartasample.transaction.jpa.CountData;

import java.io.IOException;

@Transactional
@WebServlet("/transaction")
public class TransactionServlet extends HttpServlet {

    @PersistenceContext
    private EntityManager em;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // カウントを取得
        CountData data = em.find(CountData.class, "key");

        if (data == null) {
            data = new CountData();
            data.setName("key");
            em.persist(data);
        }

        // カウントを加算
        data.setCount(data.getCount() + 1);

        invoke(request);

    }

    private void invoke(HttpServletRequest request) {

        String successStr = request.getParameter("success");

        if (successStr == null) {
            successStr = "true";
        }

        boolean success = Boolean.parseBoolean(successStr);

        if (!success) {
            throw new RuntimeException("例外発生");
        }
    }
}
