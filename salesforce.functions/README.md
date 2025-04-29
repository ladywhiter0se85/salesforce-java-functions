# Salesforce Function App

## Description

This project is an Azure Function App API designed to integrate with Salesforce, with a focus on key SObjects such as **Account**, **Opportunity**, and **Product2**. It provides a structured interface to perform standard operations including:

- **Retrieving all records** (`GET`)
- **Fetching records by ID** (`GET`)
- **Filtering records dynamically** (`GET`)
- **Creating new records** (`POST`)
- **Updating existing records** (`PATCH`)
- **Upserting records using external identifiers** (`PUT`)

The goal of the app is to offer a lightweight, extendable backend service to interact with Salesforce programmatically, using clean endpoints and OpenAPI documentation.

---

## Table of Contents

- [Salesforce Function App](#salesforce-function-app)
    - [Description](#description)
    - [Table of Contents](#table-of-contents)
    - [Current Functions](#current-functions)
    - [Function Breakdown](#function-breakdown)
        - [GET - `/getAccounts`](#get---getaccounts)
            - [Output Schema](#output-schema)
        - [GET - `/getAccountById`](#get---getaccountbyid)
            - [Parameters](#parameters)
            - [Output Schema](#output-schema-1)
        - [GET - `/getAccountsByFilter`](#get---getaccountsbyfilter)
            - [Parameters](#parameters-1)
            - [Output Schema](#output-schema-2)
        - [POST - `/postAccounts`](#post---postaccounts)
            - [Input Schema](#input-schema)
            - [Output Schema](#output-schema-3)
        - [PATCH - `/patchAccounts`](#patch---patchaccounts)
            - [Input Schema](#input-schema-1)
            - [Output Schema](#output-schema-4)
        - [PUT - `/upsertAccounts`](#put---upsertaccounts)
            - [Parameters](#parameters-2)
            - [Input Schema](#input-schema-2)
            - [Output Schema](#output-schema-5)
        - [GET - `/getOpportunities`](#get---getopportunities)
            - [Output Schema](#output-schema-6)
        - [GET - `/getOpportunityById`](#get---getopportunitybyid)
            - [Parameters](#parameters-3)
            - [Output Schema](#output-schema-7)
        - [GET - `/getOpportunitiesByFilter`](#get---getopportunitiesbyfilter)
            - [Parameters](#parameters-4)
            - [Output Schema](#output-schema-8)
        - [POST - `/postOpportunities`](#post---postopportunities)
            - [Input Schema](#input-schema-3)
            - [Output Schema](#output-schema-9)
        - [PATCH - `/patchOpportunities`](#patch---patchopportunities)
            - [Input Schema](#input-schema-4)
            - [Output Schema](#output-schema-10)
        - [PUT - `/upsertOpportunities`](#put---upsertopportunities)
            - [Parameters](#parameters-5)
            - [Input Schema](#input-schema-5)
            - [Output Schema](#output-schema-11)
        - [GET - `/getProduct2s`](#get---getproduct2s)
            - [Output Schema](#output-schema-12)
        - [GET - `/getProduct2ById`](#get---getproduct2byid)
            - [Parameters](#parameters-6)
            - [Output Schema](#output-schema-13)
        - [GET - `/getProduct2sByFilter`](#get---getproduct2sbyfilter)
            - [Parameters](#parameters-7)
            - [Output Schema](#output-schema-14)
        - [POST - `/postProduct2s`](#post---postproduct2s)
            - [Input Schema](#input-schema-6)
            - [Output Schema](#output-schema-15)
        - [PATCH - `/patchProduct2s`](#patch---patchproduct2s)
            - [Input Schema](#input-schema-7)
            - [Output Schema](#output-schema-16)
        - [PUT - `/upsertProduct2s`](#put---upsertproduct2s)
            - [Parameters](#parameters-8)
            - [Input Schema](#input-schema-8)
            - [Output Schema](#output-schema-17)


## Current Functions

This Function App exposes the following endpoints:

1. **GET** `/getAccounts` – Returns a list of Salesforce Account objects
2. **GET** `/getAccountById` – Returns a Salesforce Account object
3. **GET** `/getAccountsByFilter` – Returns a list of Salesforce Account objects
4. **POST** `/postAccounts` – Returns a list of OperationResponse
5. **PATCH** `/patchAccounts` – Returns a list of OperationResponse
6. **PUT** `/upsertAccounts` – Returns a list of OperationResponse
7. **GET** `/getOpportunities` – Returns a list of Salesforce Opportunity objects
8. **GET** `/getOpportunityById` – Returns a Salesforce Opportunity object
9. **GET** `/getOpportunitiesByFilter` – Returns a list of Salesforce Opportunity objects
10. **POST** `/postOpportunities` – Returns a list of OperationResponse
11. **PATCH** `/patchOpportunities` – Returns a list of OperationResponse
12. **PUT** `/upsertOpportunities` – Returns a list of OperationResponse
13. **GET** `/getProduct2s` – Returns a list of Salesforce Product2 objects
14. **GET** `/getProduct2ById` – Returns a Salesforce Product2 object
15. **GET** `/getProduct2sByFilter` – Returns a list of Salesforce Product2 objects
16. **POST** `/postProduct2s` – Returns a list of OperationResponse
17. **PATCH** `/patchProduct2s` – Returns a list of OperationResponse
18. **PUT** `/upsertProduct2s` – Returns a list of OperationResponse


## Function Breakdown

### GET - `/getAccounts`

Returns a list of Salesforce `Account` objects.

#### Output Schema

```json
[
  {
    "attributes": {
      "type": "Account",
      "url": "mockedURLAccountOne"
    },
    "id": "mockedAccountOneId",
    "name": "Mocked Account One",
    "type": "Customer - Direct",
    "billingAddress": {
      "street": "312 Constitution Place\nAustin, TX 78767\nUSA",
      "city": "Austin",
      "state": "TX",
      "country": "United States",
      "stateCode": "TX",
      "countryCode": "US"
    },
    "phone": "(512) 757-6000",
    "description": "Description for Mocked Account One.",
    "active__c": "Yes"
  },
  {
    "attributes": {
      "type": "Account",
      "url": "mockedURLAccountTwo"
    },
    "id": "mockedAccountTwoId",
    "name": "Mocked Account Two",
    "type": "Customer - Direct",
    "billingAddress": {
      "street": "525 S. Lexington Ave",
      "city": "Burlington",
      "state": "NC",
      "postalCode": "27215",
      "country": "USA",
      "stateCode": "NC",
      "countryCode": "US"
    },
    "phone": "(336) 222-7000"
  }
]
```


### GET - `/getAccountById`

Returns a Salesforce `Account` object.

#### Parameters

| Parameter Name | Type   | Required | In   | Example            |
|----------------|--------|----------|------|--------------------|
| accountId      | string | true     | path | mockedAccountOneId |

#### Output Schema

```json
{
  "attributes": {
    "type": "Account",
    "url": "mockedURLAccountOne"
  },
  "id": "mockedAccountOneId",
  "name": "Mocked Account One",
  "type": "Customer - Direct",
  "billingAddress": {
    "street": "312 Constitution Place\nAustin, TX 78767\nUSA",
    "city": "Austin",
    "state": "TX",
    "country": "United States",
    "stateCode": "TX",
    "countryCode": "US"
  },
  "phone": "(512) 757-6000",
  "description": "Description for Mocked Account One.",
  "active__c": "Yes"
}
```

### GET - `/getAccountsByFilter`

Returns a list of Salesforce `Account` objects.

#### Parameters

| Parameter Name | Type   | Required | In    | Example         |
|----------------|--------|----------|-------|-----------------|
| where          | string | true     | path  | BillingState:CA |
| isAnd          | bool   | true     | query | true            |

#### Output Schema

```json
[
  {
    "attributes": {
      "type": "Account",
      "url": "mockedURLAccountOne"
    },
    "id": "mockedAccountOneId",
    "name": "Mocked Account One",
    "type": "Customer - Direct",
    "billingAddress": {
      "street": "312 Constitution Place\nAustin, TX 78767\nUSA",
      "city": "Austin",
      "state": "TX",
      "country": "United States",
      "stateCode": "TX",
      "countryCode": "US"
    },
    "phone": "(512) 757-6000",
    "description": "Description for Mocked Account One.",
    "active__c": "Yes"
  },
  {
    "attributes": {
      "type": "Account",
      "url": "mockedURLAccountTwo"
    },
    "id": "mockedAccountTwoId",
    "name": "Mocked Account Two",
    "type": "Customer - Direct",
    "billingAddress": {
      "street": "525 S. Lexington Ave",
      "city": "Burlington",
      "state": "NC",
      "postalCode": "27215",
      "country": "USA",
      "stateCode": "NC",
      "countryCode": "US"
    },
    "phone": "(336) 222-7000"
  }
]
```

### POST - `/postAccounts`

Returns a list of `Operation Response` objects.

#### Input Schema

```json
[
  {
    "attributes": {
      "type": "Account"
    },
    "name": "Mocked Salesforce Account",
    "phone": "588-454-5857",
    "description": "Mocked Salesforce Account Creation"
  }
]
```

#### Output Schema

```json
[
  {
    "id": "mockedAccountId",
    "success": true
  }
]
```

### PATCH - `/patchAccounts`

Returns a list of `Operation Response` objects.

#### Input Schema

```json
[
  {
    "attributes": {
      "type": "Account"
    },
    "id": "mockedAccountId",
    "description": "Mocked Salesforce Account Update"
  }
]
```

#### Output Schema

```json
[
  {
    "id": "mockedAccountId",
    "success": true
  }
]
```

### PUT - `/upsertAccounts`

Returns a list of `Operation Response` objects.

#### Parameters

| Parameter Name | Type   | Required | In   | Example |
|----------------|--------|----------|------|---------|
| externalField  | string | true     | path | Id      |

#### Input Schema

```json
[
  {
    "attributes": {
      "type": "Account"
    },
    "id": "mockedExistingAccountId",
    "description": "Mocked Salesforce Account Upsert"
  },
  {
    "attributes": {
      "type": "Account"
    },
    "name": "Mocked Salesforce Account",
    "description": "Mocked Salesforce Account Creation",
    "phone": "252-585-6896"
  }
]
```

#### Output Schema

```json
[
  {
    "id": "mockedAccountId",
    "success": true
  },
  {
    "id": "mockedAccountIdTwo",
    "success": true,
    "created": true
  }
]
```

### GET - `/getOpportunities`

Returns a list of Salesforce `Opportunity` objects.

#### Output Schema

```json
[
  {
    "attributes": {
      "type": "Opportunity"
    },
    "id": "mockedOpportunityIdOne",
    "accountId": "mockedAccountIdOne",
    "name": "Mocked Opportunity One",
    "stageName": "Qualification",
    "amount": 15000,
    "closeDate": "2025-02-01T00:00:00",
    "description": "Description for Mocked Opportunity One.",
    "type": "New Customer"
  },
  {
    "attributes": {
      "type": "Opportunity"
    },
    "id": "mockedOpportunityIdTwo",
    "accountId": "mockedAccountIdTwo",
    "name": "Mocked Opportunity Two",
    "stageName": "Negotiation/Review",
    "amount": 125000,
    "closeDate": "2025-01-20T00:00:00",
    "description": "Description for Mocked Opportunity Two.",
    "type": "Existing Customer - Upgrade"
  }
]
```

### GET - `/getOpportunityById`

Returns a Salesforce `Opportunity` object.

#### Parameters

| Parameter Name | Type   | Required | In   | Example                |
|----------------|--------|----------|------|------------------------|
| opportunityId  | string | true     | path | mockedOpportunityOneId |

#### Output Schema

```json
{
  "attributes": {
    "type": "Opportunity"
  },
  "id": "mockedOpportunityIdOne",
  "accountId": "mockedAccountIdOne",
  "name": "Mocked Opportunity One",
  "stageName": "Qualification",
  "amount": 15000,
  "closeDate": "2025-02-01T00:00:00",
  "description": "Description for Mocked Opportunity One.",
  "type": "New Customer"
}
```

### GET - `/getOpportunitiesByFilter`

Returns a list of Salesforce `Opportunity` objects.

#### Parameters

| Parameter Name | Type   | Required | In    | Example                 |
|----------------|--------|----------|-------|-------------------------|
| where          | string | true     | path  | stageName:Qualification |
| isAnd          | bool   | true     | query | true                    |

#### Output Schema

```json
[
  {
    "attributes": {
      "type": "Opportunity"
    },
    "id": "mockedOpportunityIdOne",
    "accountId": "mockedAccountIdOne",
    "name": "Mocked Opportunity One",
    "stageName": "Qualification",
    "amount": 15000,
    "closeDate": "2025-02-01T00:00:00",
    "description": "Description for Mocked Opportunity One.",
    "type": "New Customer"
  },
  {
    "attributes": {
      "type": "Opportunity"
    },
    "id": "mockedOpportunityIdTwo",
    "accountId": "mockedAccountIdTwo",
    "name": "Mocked Opportunity Two",
    "stageName": "Negotiation/Review",
    "amount": 125000,
    "closeDate": "2025-01-20T00:00:00",
    "description": "Description for Mocked Opportunity Two.",
    "type": "Existing Customer - Upgrade"
  }
]
```

### POST - `/postOpportunities`

Returns a list of `Operation Response` objects.

#### Input Schema

```json
[
  {
    "attributes": {
      "type": "Opportunity"
    },
    "name": "Mocked Salesforce Opportunity",
    "description": "Mocked Salesforce Opportunity Creation",
    "stageName": "Qualification",
    "closeDate": "2025-04-15"
  }
]
```

#### Output Schema

```json
[
  {
    "id": "mockedOpportunityId",
    "success": true
  }
]
```

### PATCH - `/patchOpportunities`

Returns a list of `Operation Response` objects.

#### Input Schema

```json
[
  {
    "attributes": {
      "type": "Opportunity"
    },
    "id": "mockedOpportunityId",
    "description": "Mocked Salesforce Opportunity Update"
  }
]
```

#### Output Schema

```json
[
  {
    "id": "mockedOpportunityId",
    "success": true
  }
]
```

### PUT - `/upsertOpportunities`

Returns a list of `Operation Response` objects.

#### Parameters

| Parameter Name | Type   | Required | In   | Example |
|----------------|--------|----------|------|---------|
| externalField  | string | true     | path | Id      |

#### Input Schema

```json
[
  {
    "attributes": {
      "type": "Opportunity"
    },
    "id": "mockedExistingOpportunityId",
    "description": "Mocked Salesforce Opportunity Upsert"
  },
  {
    "attributes": {
      "type": "Opportunity"
    },
    "name": "Mocked Salesforce Opportunity",
    "description": "Mocked Salesforce Opportunity Creation",
    "stageName": "Needs Analysis",
    "closeDate": "2025-04-15"
  }
]
```

#### Output Schema

```json
[
  {
    "id": "mockedOpportunityId",
    "success": true
  },
  {
    "id": "mockedOpportunityIdTwo",
    "success": true,
    "created": true
  }
]
```


### GET - `/getProduct2s`

Returns a list of Salesforce `Product2` objects.

#### Output Schema

```json
[
  {
    "attributes": {
      "type": "Product2"
    },
    "id": "mockedProduct2IdOne",
    "name": "Mocked Software Subscription Product One",
    "productCode": "S001",
    "description": "Mocked Software Subscription Product One Description",
    "isActive": true,
    "family": "Software",
    "type__c": "Subscription"
  },
  {
    "attributes": {
      "type": "Product2"
    },
    "id": "mockedProduct2IdTwo",
    "name": "Mocked Hardware Base Product Product One",
    "productCode": "H001",
    "description": "Mocked Hardware Base Product Product One Description",
    "isActive": true,
    "family": "Hardware",
    "type__c": "Base Product"
  }
]
```


### GET - `/getProduct2ById`

Returns a Salesforce `Product2` object.

#### Parameters

| Parameter Name | Type   | Required | In   | Example            |
|----------------|--------|----------|------|--------------------|
| accountId      | string | true     | path | mockedProduct2OneId |

#### Output Schema

```json
{
  "attributes": {
    "type": "Product2"
  },
  "Id": "mockedProduct2IdOne",
  "Name": "Mocked Software Subscription Product One",
  "ProductCode": "S001",
  "Description": "Mocked Software Subscription Product One Description",
  "IsActive": true,
  "Family": "Software",
  "Type__c": "Subscription"
}
```

### GET - `/getProduct2sByFilter`

Returns a list of Salesforce `Product2` objects.

#### Parameters

| Parameter Name | Type   | Required | In    | Example         |
|----------------|--------|----------|-------|-----------------|
| where          | string | true     | path  | Family:Maintenance,Software |
| isAnd          | bool   | true     | query | true            |

#### Output Schema

```json
[
  {
    "attributes": {
      "type": "Product2"
    },
    "id": "mockedProduct2IdOne",
    "name": "Mocked Software Subscription Product One",
    "productCode": "S001",
    "description": "Mocked Software Subscription Product One Description",
    "isActive": true,
    "family": "Software",
    "type__c": "Subscription"
  },
  {
    "attributes": {
      "type": "Product2"
    },
    "id": "mockedProduct2IdTwo",
    "name": "Mocked Hardware Base Product Product One",
    "productCode": "H001",
    "description": "Mocked Hardware Base Product Product One Description",
    "isActive": true,
    "family": "Hardware",
    "type__c": "Base Product"
  }
]
```

### POST - `/postProduct2s`

Returns a list of `Operation Response` objects.

#### Input Schema

```json
[
  {
    "attributes": {
      "type": "Product2"
    },
    "Id": "mockedProduct2IdOne",
    "Name": "Mocked Software Subscription Product One",
    "ProductCode": "S001",
    "Description": "Mocked Software Subscription Product One Description",
    "IsActive": true,
    "Family": "Software",
    "Type__c": "Subscription"
  }
]
```

#### Output Schema

```json
[
  {
    "id": "mockedProduct2Id",
    "success": true
  }
]
```

### PATCH - `/patchProduct2s`

Returns a list of `Operation Response` objects.

#### Input Schema

```json
[
  {
    "attributes": {
      "type": "Product2"
    },
    "Id": "mockedProduct2IdOne",
    "Description": "Mocked Software Subscription Product One Description"
  }
]
```

#### Output Schema

```json
[
  {
    "id": "mockedProduct2Id",
    "success": true
  }
]
```

### PUT - `/upsertProduct2s`

Returns a list of `Operation Response` objects.

#### Parameters

| Parameter Name | Type   | Required | In   | Example |
|----------------|--------|----------|------|---------|
| externalField  | string | true     | path | Id      |

#### Input Schema

```json
[
  {
    "attributes": {
      "type": "Product2"
    },
    "id": "mockedProduct2IdOne",
    "description": "Mocked Software Subscription Product One Description"
  },
  {
    "attributes": {
      "type": "Product2"
    },
    "id": "mockedProduct2IdTwo",
    "name": "Mocked Hardware Base Product Product One",
    "productCode": "H001",
    "description": "Mocked Hardware Base Product Product One Description",
    "isActive": true,
    "family": "Hardware",
    "type__c": "Base Product"
  }
]
```

#### Output Schema

```json
[
  {
    "id": "mockedProduct2Id",
    "success": true
  },
  {
    "id": "mockedProduct2IdTwo",
    "success": true,
    "created": true
  }
]
```
