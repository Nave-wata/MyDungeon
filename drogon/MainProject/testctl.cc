#include "testctl.h"

void testctl::getJson(
    const HttpRequestPtr &req,
    std::function<void(const HttpResponsePtr &)> &&callback) const {
        Json::Value ret;
        Json::Value Sample;
        Json::array a;

        Sample["Name"] = "Nave-wata";
        Sample["age"] = 19;
        Sample["live"] = "Osaka";

        ret["Sample"] = Sample;

        auto resp = HttpResponse::newHttpJsonResponse(ret);
        callback(resp);
    }