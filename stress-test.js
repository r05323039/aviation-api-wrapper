import http from 'k6/http';
import { check, sleep } from 'k6';

export const options = {
    vus: 10,
    duration: '15s',
    thresholds: {
        http_req_failed: ['rate<0.01'],
        http_req_duration: ['p(95)<50'],
    },
};

export default function () {
    const res = http.get('http://localhost:8080/api/v1/airports/KLAX');

    check(res, {
        'status is 200': (r) => r.status === 200, // Fallback 應該回傳 200
        'body is empty list': (r) => r.body.includes('[]') || r.body.length < 50, // 驗證是 Fallback 資料
    });

    sleep(0.1);
}