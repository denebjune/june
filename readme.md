API
**读取transactions列表**

| Method | url                  |
|--------|----------------------|
| GET    | /api/v1/transactions |
|        |                      |

返回值（example）
[
{
id: 1,
serialNumber: "00001",
title: "Transaction 1",
description: "this is Transaction 1",
createdAt: "2025-03-04T11:36:43.630+00:00",
updatedAt: "2025-03-04T11:36:43.630+00:00",
amount: 1000.2
},
{
id: 2,
serialNumber: "00002",
title: "Transaction 2",
description: "this is Transaction 2",
createdAt: "2025-03-04T11:36:43.635+00:00",
updatedAt: "2025-03-04T11:36:43.635+00:00",
amount: -200
},
{
id: 3,
serialNumber: "00003",
title: "Transaction 3",
description: "this is Transaction 3",
createdAt: "2025-03-04T11:36:43.635+00:00",
updatedAt: "2025-03-04T11:36:43.635+00:00",
amount: 501.1
}
]

**创建transaction**

| method | url                 | content-type                      |
|--------|---------------------|-----------------------------------|
| post   | /v1/api/transaction | application/x-www-form-urlencoded |

参数

| 参数key        | 参数value（example）      | 参数类型       | 参数描述  |   |
|--------------|-----------------------|------------|-------|---|
| title        | transaction 1         | 字符串 string | 标题    |   |
| description  | this is transaction 1 | 字符串 string | 描述    |   |
| serialNumber | 00001                 | 字符串 string | 唯一序列号 |   |
| amount       | 100.00                | 浮点型 数字     | 金额    |   |

错误码定义
1003 冲突，已存在相同serialNumber的实体
1002 参数缺失 parameter missing
1005 服务器异常 server error

**读取单个transaction信息**

| method | url                      |
|--------|--------------------------|
| get    | /api/v1/transaction/{id} |

返回值 example
{
id: 1,
serialNumber: "00001",
title: "Transaction 1",
description: "this is Transaction 1",
createdAt: "2025-03-04T11:36:43.630+00:00",
updatedAt: "2025-03-04T11:36:43.630+00:00",
amount: 1000.2
}

**修改transaction**

| method | url                      | content-type                      |
|--------|--------------------------|-----------------------------------|
| put    | /api/v1/transaction/{id} | application/x-www-form-urlencoded |

参数 parameter

| 参数key        | 参数value（example）      | 参数类型       | 参数描述  |   |
|--------------|-----------------------|------------|-------|---|
| title        | transaction 1         | 字符串 string | 标题    |   |
| description  | this is transaction 1 | 字符串 string | 描述    |   |
| serialNumber | 00001                 | 字符串 string | 唯一序列号 |   |
| amount       | 100.00                | 浮点型 数字     | 金额    |   |

错误码定义
1004 不存在{id}的transaction
1003 冲突，已存在相同serialNumber的实体
1002 参数缺失 parameter missing
1005 服务器异常 server error

**删除transaction**

| method | url                      |
|--------|--------------------------|
| delete | /api/v1/transaction/{id} |

错误码 error code
1004 不存在{id}的transaction
1005 服务器异常

**单测**
com.june.transaction.TransactionApplicationTests

| 编号 | 测试方法名                                     | 测试用例描述                     |   |
|----|-------------------------------------------|----------------------------|---|
| 1  | contextLoads                              | 测试数据加载完成                   |   |
| 2  | testNotExistTransaction                   | 查找不存在的{id}的transaction     |   |
| 3  | testExistTransaction                      | 查找存在的{id}                  |   |
| 4  | testCreateTransaction                     | 测试正常创建流程                   |   |
| 5  | testCreateDuplicateTransaction            | 测试创建冲突（重复的serial number)   |   |
| 6  | testCreateTransactionWithParameterMissing | 测试缺乏参数的创建操作                |   |
| 7  | testUpdateTransaction                     | 测试正常修改流程                   |   |
| 8  | testUpdateTransactionConflict             | 测试修改导致冲突的流程                |   |
| 9  | testUpdateTransactionNotExist             | 测试尝试修改不存在的{id}的transaction |   |
| 10 | testDeleteTransaction                     | 测试删除流程                     |   |
| 11 | testDeleteTransactionNotExist             | 测试删除不存在的{id}               |   |
| 12 | testUpdateTransactionWithParameterMissing | 测试修改参数缺失                   |   |

**部分stress test**
by jmeter
列表

| Label  | # 样本   | 平均值 | 最大值  | 最小值 | 标准偏差   | 吞吐量            | 异常 % |
|--------|--------|-----|------|-----|--------|----------------|------|
| HTTP请求 | 100000 | 121 | 2393 | 0   | 155.78 | 7468.81769/sec | 0%   |
| 总体     | 100000 | 121 | 2393 | 0   | 155.78 | 7468.81769/sec | 0%   |

update

| Label  | # 样本   | 平均值 | 最大值  | 最小值 | 标准偏差   | 吞吐量            | 异常 % |
|--------|--------|-----|------|-----|--------|----------------|------|
| HTTP请求 | 59415 | 505 | 2935 | 1   | 578.84 | 1925.68225/sec | 0%   |
| 总体     | 59415 | 505 | 2935 | 1   | 578.84 | 1925.68225/sec   | 0%   |
