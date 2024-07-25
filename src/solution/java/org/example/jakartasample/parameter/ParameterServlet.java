package org.example.jakartasample.parameter;

import jakarta.inject.Inject;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;

import java.io.IOException;

@WebServlet("/hello")
public class ParameterServlet extends HttpServlet {

    @Inject
    Client client;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 接続先のサーバを設定
        WebTarget target = client.target(getTargetURL());

        // 別のサーバへリクエストを実行
        MyMessage message = target
                .request(MediaType.APPLICATION_JSON)
                .get(MyMessage.class);

        // 取得したレスポンスを JSP でも使えるようにする
        request.setAttribute("message", message.message);

        // 処理を JSP へ移す
        RequestDispatcher dispatcher = request.getRequestDispatcher("/hello.jsp");
        dispatcher.forward(request, response);
    }

    /**
     * 接続先となるサーバの URL を生成するメソッド
     * URL は固定の文字列として実装されており、変更できない。
     * 本来はこのようにメソッドとして定義されることはなく、文字列が直接記載されていることが多いです。
     * 本コードでは、もう１つの実装と比較しやすいようにメソッドとして定義している。
     *
     * @return
     */
    private String getTargetURL() {
        return "http://localhost:8082/echo/HelloWorld";
    }

    /**
     * 接続先となるサーバの URL を生成するメソッド
     * URL はホスト名を環境変数から取得し、他は固定になっている
     * 環境変数だけではなく、システムプロパティ、プロパティファイルなどで実現できる
     * URL 全体を外部から取得することも可能。
     *
     * @return
     */
//    private String getTargetURL(){
//        String hostname = System.getenv().getOrDefault("MICROSERVICE_HOSTNAME", "localhost:8082");
//        return "http://" + hostname + "/echo/HelloWorld";
//    }
}
