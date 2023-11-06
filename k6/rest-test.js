import http from 'k6/http';
import { sleep } from 'k6';

export default function () {
  http.get('http://localhost:8080/rest/api/greeting/sayHello');
  sleep(1);
}


//$ k6 run --vus 10 --duration 30s script.js