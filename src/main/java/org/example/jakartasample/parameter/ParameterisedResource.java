package org.example.jakartasample.parameter;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

@Path("/jee/parameter")
public class ParameterisedResource {

    /**
     * システムプロパティから情報を取得するサンプル
     * javaコマンドに -D オプションで付与するもの
     * @return
     */
    @GET
    @Path("/system")
    @Produces(MediaType.APPLICATION_JSON)
    public String properties() {
        return System.getProperty("app.properties", "システムプロパティ・デフォルト値");
    }

    /**
     * 環境変数から情報を取得するサンプル
     * OSの環境変数
     * @return
     */
    @GET
    @Path("/env")
    @Produces(MediaType.APPLICATION_JSON)
    public String env(){
        return System.getenv().getOrDefault("key", "環境変数・デフォルト値");
    }

    /**
     * プロパティファイファイルから読み込む
     */
    @GET
    @Path("/file")
    @Produces(MediaType.APPLICATION_JSON)
    public String readProperties() throws IOException {
        Properties p = new Properties();
        //TODO: ファイルを指定
        p.load(new FileReader(""));
        return p.getProperty("key", "ファイル・デフォルト値");
    }

    /**
     * JEEのコンテキストから取得
     */
    @GET
    @Path("/context")
    @Produces(MediaType.APPLICATION_JSON)
    public String context() throws NamingException {
        Context context = new InitialContext();
        String lookup = (String) context.lookup("java:comp/key");
        return lookup == null ? "コンテキスト・デフォルト値" : lookup;
    }
}
