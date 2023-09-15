package github.serliunx.varytalk.common.result;

import java.util.Collection;

public class CountResult extends Result{
    private int count;

    public CountResult(Object data, String message, int status, int count) {
        super(data, message, status);
        this.count = count;
    }

    public static CountResult success(Object data, String message, int status, int size){
        return new CountResult(data, message, status, size);
    }

    public static <T extends Collection<?>> CountResult success(T t, String message, int status, int size){
        return new CountResult(t, message, status, size);
    }

    public static <T extends Collection<?>> CountResult success(T t, String message){
        return success(t, message, 200, t.size());
    }

    public static <T extends Collection<?>> CountResult success(T t){
        return success(t, "操作成功", 200, t.size());
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }
}
