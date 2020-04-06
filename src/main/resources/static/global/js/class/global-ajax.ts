/// <reference path="../global.d.ts" />

class GlobalAjax {
    private static responseData : {[key : string] : any};

    /**
     * Ajax데이터만 가져와야 할 경우 string을 파라미터로, 혹은 AjaxSpec을 파라미터로 넣어주면 됨.
     * 만약, 기존에 Ajax를 통하여서 데이터를 가져온 적이 있으면 그 데이터를 가져온다. (모두)
     * 만약, 기존에 Ajax를 통하여 데이터를 가져온 적이 없으면, 새로 init하여 가져온다. (AjaxSpec 을 파라미터로 넣어준 경우에만.)
     * @param data
     */
    public static get(data : string | AjaxSpec) : JQueryPromise<Response> {
        const deferred = $.Deferred<Response>();
        const instanceName = typeof data === "string" ? data : data.getName();

        if(!!GlobalAjax.responseData[instanceName]) {
            deferred.resolve(GlobalAjax.responseData[instanceName]);
            return deferred.promise();
        }

        if (typeof data !== "string") {
            this.init(data).done((res : any) => {
                deferred.resolve(res)
            }).fail((err) => {
                console.error(err);
                deferred.reject(err);
            });
        }
        return deferred.promise();
    }

    /**
     * Ajax데이터가 무조건 초기화 되어야하는 경우. 초기화하여 가져온다.
     * @param ajaxSpec
     */
    public static init(ajaxSpec : AjaxSpec) : JQueryPromise<Response> {
        const deferred = $.Deferred<Response>();

        $.ajax(ajaxSpec.getSetting()).done((res : JQueryPromiseCallback<Response>) => {
            GlobalAjax.responseData[ajaxSpec.getName()] = res;
            deferred.resolve(GlobalAjax.responseData[ajaxSpec.getName()]);
        }).fail(err =>{
            deferred.reject(err);
        });
        return deferred.promise();
    }
}

/**
 * 기본적인 AjaxSpec.
 */
abstract class DefaultAjaxSpec implements AjaxSpec{
    protected name : string;
    protected ajaxSetting : JQueryAjaxSettings;

    protected constructor(name : string, ajaxSetting : JQueryAjaxSettings){
        this.name = name;
        this.ajaxSetting = ajaxSetting;
    }

    public getSetting(){
        return this.ajaxSetting;
    }

    public getName() {
        return this.name;
    }
}