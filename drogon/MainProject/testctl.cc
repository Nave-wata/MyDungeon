#include "testctl.h"

void testctl::getJson(
    const HttpRequestPtr &req, 
    std::function<void(const HttpResponsePtr &)> &&callback) const {
        Json::Value ret;

        ret["Name"] = "Nave-wata";
        ret["age"] = 19;
        ret["live"] = "Osaka";

        auto resp = HttpResponse::newHttpJsonResponse(ret);
        callback(resp);
    }