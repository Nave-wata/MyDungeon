#pragma once

#include <drogon/HttpController.h>

using namespace drogon;

class testctl : public drogon::HttpController<testctl>
{
  public:
    METHOD_LIST_BEGIN
    
    METHOD_ADD(testctl::getJson, "/", Get);

    METHOD_LIST_END
    
    void getJson(const HttpRequestPtr &req,
                std::function<void(const HttpResponsePtr &)> &&callback) const;
};
