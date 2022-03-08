#include "testctl.h"

void testctl::getJson(
    const HttpRequestPtr &req, 
    std::function<void(const HttpResponsePtr &)> &&callback) const {
        Json::Value ret;

        ret["Sample"]["Name"] = "Nave-wata";
        ret["Sample"]["age"] = 19;
        ret["Sample"]["live"] = "Osaka";

        auto resp = HttpResponse::newHttpJsonResponse(ret);
        callback(resp);
    }