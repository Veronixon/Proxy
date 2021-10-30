import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class Main {
    public static void main(String[] args) {
/*        Reader reader = new ProxyReader();
        reader.reader("Hi");*/

        InvocationHandler invocationHandler = new ReaderInvocationHandler();
        Object proxyInstance = Proxy.newProxyInstance(Main.class.getClassLoader(), new Class[]{Reader.class}, invocationHandler);
        ((Reader) proxyInstance).reader("hi");
    }
}

interface Reader {
    String reader(String str);
}

class MyReader implements Reader {
    @Override
    public String reader(String str) {
        return str + "!";
    }
}

class ReaderInvocationHandler implements InvocationHandler {
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println(args[0]);
        Object result = method.invoke(new MyReader(), args);
        System.out.println(result);
        return result;
    }
}

class ProxyReader extends MyReader {

    @Override
    public String reader(String str) {
        System.out.println(str);
        String read = super.reader(str);
        System.out.println(read);
        return read;
    }
}
