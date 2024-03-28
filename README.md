# Service TOTP

algorithm-totp

## Create docker image

Use the build command to create de Docker Image

```bash
docker build --tag service-totp .
```

Use the build command to create de Docker Container

```bash
docker run -d -p 8088:8088  --name service-totp -v /opt/logs:/app/logs --network siipne-network service-totp
```