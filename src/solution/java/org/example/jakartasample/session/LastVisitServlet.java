package org.example.jakartasample.session;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/last")
public class LastVisitServlet extends HttpServlet {

    private static final Map<String, LocalDateTime> lastVisits = new HashMap<>();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Object lastVisit = this.readLastVisit(request);

        if (lastVisit == null) {
            request.setAttribute("lastVisit", "はじめてのログインです");
        } else {
            request.setAttribute("lastVisit", lastVisit);
        }

        this.writeLastVisit(request);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/session/lastVisit.jsp");
        dispatcher.forward(request, response);
    }

    /**
     * セッションデータを読み込むメソッド
     * このメソッドは、独自の実装によって実現している。
     * この例では、自プロセス内にセッションデータを管理している。
     * セッションデータは、プロセスが停止すると消失し、他のAPサーバとは共有されない。
     * セッションデータは、RDBMSやRedisなどに保存すると、プロセスの停止でも消失せず、複数のAPサーバでデータを共有できる。
     * しかし、セッションデータの読み込みと書き込みでネットワーク通信が複数回発生するため、処理全体の応答は長くなる。
     *
     * @param request
     * @return
     */
    private Object readLastVisit(HttpServletRequest request) {
        return lastVisits.get(request.getSession().getId());
    }

    /**
     * セッションデータを読み込むメソッド
     * このメソッドは、Jakarta EE の API を使用して実現している。
     * Jakarta EE の API は、HttpSession クラスを使う方法と、@SessionScoped アノテーションを使う方法がある。
     * セッションデータの格納方法は AP サーバに委ねられる。
     * たとえば、AP サーバは、よく使うセッションデータだけをメモリ上に保存し、書き込みはネットワークを介して永続化することもできる
     *
     * @param request
     * @return
     */
//    private Object readLastVisit(HttpServletRequest request) {
//        HttpSession session = request.getSession();
//        return session.getAttribute("lastVisit");
//    }

    /**
     * セッションデータを書き込むメソッド
     *
     * @param request
     */
    private void writeLastVisit(HttpServletRequest request) {
        lastVisits.put(request.getSession().getId(), LocalDateTime.now());
    }

    /**
     * セッションデータを書き込むメソッド
     *
     * @param request
     */
//    private void writeLastVisit(HttpServletRequest request) {
//        HttpSession session = request.getSession();
//        session.setAttribute("lastVisit", LocalDateTime.now());
//    }
}
