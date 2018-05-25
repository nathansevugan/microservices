FROM fluent/fluentd:v0.12-onbuild
USER root
RUN ["gem", "install", "fluent-plugin-elasticsearch", "--no-rdoc", "--no-ri", "--version", "1.9.2"]
USER fluent

