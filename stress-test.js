import http from 'k6/http';
import { check, sleep } from 'k6';

export const options = {
    vus: 10,
    duration: '15s',
    thresholds: {
        checks: ['rate==1.0'],
    },
};

export default function () {
    const res = http.get('http://localhost:8080/api/v1/airports/KLAX');

    const isSafe = check(res, {
        'System is Stable (200 OK or 503 Fallback)': (r) => r.status === 200 || r.status === 503,
    });

    if (!isSafe) {
        console.log(`🔥 CRASH DETECTED! Status: ${res.status} Body: ${res.body}`);
    }

    sleep(0.1);
}