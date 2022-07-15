Claims Management Project
---------------------------------------------------------------------------------------------------------------------------
to run the project
1. import the backend folder into your eclipse as maven project
2. then right click on every project -> maven -> update Project
3. run EurekaServer
4. run ZuulGateway
5. run authorization service
6. run policy service
7. run claims service
8. run member service

after running all these service follow the steps
1. go to the member portal folder 
2. open vscode in that directory
3. run ng serve
4. go to browser open http://localhost:4200


Swagger links
------------------------------------------------------------------------------------------------------------------------------
Authorization Service - http://localhost:8008/swagger-ui.html
Policy Service - http://localhost:8081/policy/swagger-ui.html
Claim Service - http://localhost:8010/swagger-ui.html
Member Service - http://localhost:8099/swagger-ui.html


Eureka Server
--------------
link: http://localhost:8761/
port : 8761


Zuul Gateway
------------
port: 9797
service name: Zuul-Server

Authorizatio Service
--------------------
port: 8008
service name: authorization-service

    End Points
    ----------
    1.login: (post) http://localhost:8008/authorization/login
      json: {
            "username":"hrithik",
            "password":"password"
        }
      output:{
                "username": "hrithik",
                "jwtAuthToken": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJocml0aGlrIiwiZXhwIjoxNjU3ODIyMjcxLCJpYXQiOjE2NTc4MjA0NzF9.OU44FvMoSM3pJL-Tkxq7zXqfHxKRwl24UJI9C06vlFY",
                "serverCurrentTime": 1657820471729,
                "tokenExpirationTime": 1657822271729
            }
    
    2.validate token: (get) http://localhost:8008/authorization/validate
      inside the header set 
      Authorization = bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ2YWlzaG5hdmkiLCJleHAiOjE2NTc4MTcjYsImlhdCI6MTY1NzgxNTI2Nn0.GFAwCL4sL86JNPUWFMXi87th528oW1GTZ73dS8Krwkg(token)
      output:{
            "validStatus": true
      }

Policy Service
---------------
port: 8081
service name: policy-service

    End Points
    ----------
    1. Get Chain of providers: (get) http://localhost:8081/policy/getChainOfProviders/P1003
       inside the header set 
      Authorization = bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ2YWlzaG5hdmkiLCJleHAiOjE2NTc4MTcjYsImlhdCI6MTY1NzgxNTI2Nn0.GFAwCL4sL86JNPUWFMXi87th528oW1GTZ73dS8Krwkg(token)
      output:{
         "providers": [
                {
                    "hospitalId": "H1",
                    "name": "Apollo Hospital",
                    "location": "Delhi-Indraprastha"
                },
                {
                    "hospitalId": "H6",
                    "name": "Fortis Memorial Research Institute",
                    "location": "Gurgaon"
                },
                {
                    "hospitalId": "H5",
                    "name": "Max Superspecialty Hospital, Saket",
                    "location": "Delhi-New Delhi"
                },
                {
                    "hospitalId": "H3",
                    "name": "Fortis Escorts Heart Institute",
                    "location": "Delhi-Okhla"
                }
            ]
      }

    2.Get Eligible Benifits: (get) http://localhost:8081/policy/getEligibleBenefits/P1002/M101
        inside the header set 
      Authorization = bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ2YWlzaG5hdmkiLCJleHAiOjE2NTc4MTcjYsImlhdCI6MTY1NzgxNTI2Nn0.GFAwCL4sL86JNPUWFMXi87th528oW1GTZ73dS8Krwkg(token)
      output: {
        "benefits": [
                        {
                            "benefitId": "B104",
                            "benefitName": "Ambulance charges upto 3000 covered"
                        },
                        {
                            "benefitId": "B101",
                            "benefitName": "Coverage for COVID-19"
                        },
                        {
                            "benefitId": "B102",
                            "benefitName": "Coverage for hospitalization at home"
                        },
                        {
                            "benefitId": "B107",
                            "benefitName": "Hospitalization charges for Deluxe room covered"
                        }
            ]
      }

    3.Get Eligible Claim Amount: (get) http://localhost:8081/policy/getEligibleClaimAmount/P1001/M102
        inside the header set 
      Authorization = bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ2YWlzaG5hdmkiLCJleHAiOjE2NTc4MTcjYsImlhdCI6MTY1NzgxNTI2Nn0.GFAwCL4sL86JNPUWFMXi87th528oW1GTZ73dS8Krwkg(token)
      output: {
            "eligibleAmount": 500000.0
      }

Claim Service
--------------
port: 8010
service name: claim-service

    End Points
    ----------
    1.Get Claim Status: (get) http://localhost:8010/claimModule/getClaimStatus/abcd
      inside header set
      Authorization =  bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJocml0aGlrIiwiZXhwIjoxNjU3ODI0NDg4LCJpYXQiOjE2NTc4MjI2ODh9.HVAJxm4OVyLfEV_JAzXSWacscnHAn5zTU3lR1dXWRms(token)
      output :{
        "claimId": "abcd",
        "claimStatus": "Pending Action",
        "claimDescription": "All the fields are successfully verified! Please wait for furthur action"
      }

    2.Submit Claim: (post) http://localhost:8010/claimModule/submitClaim
      json: {
            "claimId": "akjdfkadjf;ajf",
            "remarks":"Urgent",
            "claimAmount": 500000.00,
            "hospitalId": "H1",
            "benefitId": "B101",
            "policyId": "P1001",
            "memberId": "M101"
        }
     inside header ser
     Authorization = bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJocml0aGlrIiwiZXhwIjoxNjU3ODI0NDg4LCJpYXQiOjE2NTc4MjI2ODh9.HVAJxm4OVyLfEV_JAzXSWacscnHAn5zTU3lR1dXWRms(token)
     output: {
        "claimId": "akjdfkadjf;ajf",
        "claimStatus": "Pending Action",
        "claimDescription": "All the fields are successfully verified! Please wait for furthur action"
     }

Member Service
--------------
port: 8099
Service Name: member-service

    End Points
    ----------
    1.View Bills: (get) http://localhost:8099/memberModule/viewBills/M102
      inside header set
      Authorization = bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJocml0aGlrIiwiZXhwIjoxNjU3ODI0NDg4LCJpYXQiOjE2NTc4MjI2ODh9.HVAJxm4OVyLfEV_JAzXSWacscnHAn5zTU3lR1dXWRms (token)
      output: {
            "memberId": "M102",
            "policyId": "P103",
            "lastPaidDate": "2019-12-10",
            "dueAmount": 50500,
            "lateCharge": 6500,
            "dueDate": "2020-12-10"
        }

    2.Get Claim Status: (get) http://localhost:8099/memberModule/getClaimStatus/abcd
      inside header set
      Authorization = bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJocml0aGlrIiwiZXhwIjoxNjU3ODI0NDg4LCJpYXQiOjE2NTc4MjI2ODh9.HVAJxm4OVyLfEV_JAzXSWacscnHAn5zTU3lR1dXWRms(token)
      output: {
            "claimId": "abcd",
            "claimStatus": "Pending Action",
            "claimDescription": "All the fields are successfully verified! Please wait for furthur action"
        }

    3.Submit Claim: (post) http://localhost:8099/memberModule/submitClaim
      inside header set
      Authorization = bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJocml0aGlrIiwiZXhwIjoxNjU3ODI0NDg4LCJpYXQiOjE2NTc4MjI2ODh9.HVAJxm4OVyLfEV_JAzXSWacscnHAn5zTU3lR1dXWRms(token)
      json: {
            "claimId": "bcdef",
            "remarks":"Urgent",
            "claimAmount": 500000.00,
            "hospitalId": "H1",
            "benefitId": "B101",
            "policyId": "P1001",
            "memberId": "M101"
        }
      output: {
            "claimId": "88e40bd9-8c08-4671-821d-55b970b37046",
            "claimStatus": "Pending Action",
            "claimDescription": "All the fields are successfully verified! Please wait for furthur action"
        }
