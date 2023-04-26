## Micronaut + Micrometer

# Run project
```bash
./gradlew build run
```
# Run Prometheus and Grafana
1. Clone augustomarinho/docker-composes.git
```bash
git clone https://github.com/augustomarinho/docker-composes.git
```

2. Run Prometheus
```bash
docker-compose -f ./prometheus/docker-compose.yml up -d --force-recreate
```
The Prometheus can be access in http://localhost:9000/

3. Run Grafana
```bash
docker-compose -f ./grafana/docker-compose.yml up -d --force-recreate
```
The Grafana can be access in http://localhost:9001/

## Grafana

### Login 
Follow these steps:
* In the login page fill `admin` for user and `admin` for password
* Grafana will ask you to create a new password. Change the password

### Configure Datasource to access Prometheus
* Access Configuration/Data Sources
* Click on `Add data source`
* Select Prometheus
* In field HTTP/ URL fill with `http://host.docker.internal:9000`
* Click on Save & Test

After these steps, you'll see the popup message `Data source is working`

# Generate Metrics
Timer Metrics
```bash
curl -X GET "http://localhost:8084/v1/api/metrics/timer?name=augusto"
```
DistributionSummary
```bash
curl -X GET "http://localhost:8084/v1/api/metrics/histogram?name=augusto"
```

Counter
```bash
curl -X GET "http://localhost:8084/v1/api/metrics/counter?name=augusto"
```