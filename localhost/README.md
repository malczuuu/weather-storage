# 🐳 Docker Compose: MongoDB Replica Set & WireMock 🐳

This `docker-compose.yaml` sets up two services for local development and testing:

- **[MongoDB 8.0.6](https://hub.docker.com/_/mongo)** running as a single-node replica set
- **[WireMock 3.13.1](https://hub.docker.com/r/wiremock/wiremock)** with file-based mappings and global response templating enabled

## Table of Contents

- [Services](#services)
  - [MongoDB](#mongodb)
  - [WireMock](#wiremock)
- [Usage](#usage)
- [Development Tips](#development-tips)
  - [MongoDB](#mongodb)
  - [WireMock](#wiremock)

## Services

### MongoDB

- **Image:** [`mongo:8.0.6-noble`](https://hub.docker.com/_/mongo)
- **Ports:** Exposes port `27017` on `127.0.0.1`
- **Replica Set:** Configured as a single-node replica set (`rs0`) to enable transactions and change streams
- **Volumes:**
  - `mongo_data` → `/data/db`
  - `mongo_config` → `/data/configdb`
- **Healthcheck:**
  - Automatically initializes the replica set if not yet initiated
  - Uses `mongosh` to run a JavaScript command every 5 seconds, with a maximum of 30 retries

### WireMock

- **Image:** [`wiremock/wiremock:3.13.1-alpine`](https://hub.docker.com/r/wiremock/wiremock)
- **Ports:** Exposes port `8080` as `127.0.0.1:1080`
- **Volumes:**
  - `./wiremock/__files` → Contains static response bodies (read-only)
  - `./wiremock/mappings` → Contains mapping definitions (read-only)
- **Command Flags:**
  - `--global-response-templating` enables dynamic templating in response bodies

## Usage

**Start the services:**

```bash
docker compose up -d
```

**View logs:**

```bash
docker compose logs -f
```

**Stop the services:**

```bash
docker compose down
```

**Remove services with volumes:**

```bash
docker compose down -v
```

## Development Tips

### MongoDB

**Check Replica Set Status:**

```bash
docker exec -it <mongo_container_name> mongosh --eval "rs.status()"
```
**Connect using URI:**

```bash
mongodb://localhost:27017/?replicaSet=rs0
```

### WireMock

- **Admin UI:** http://localhost:1080/__admin
- **Add mappings at runtime via REST API:** https://wiremock.org/docs/api/
