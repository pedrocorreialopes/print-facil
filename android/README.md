# Base Android (Kotlin)

Este é um ponto de partida para integrar a interface web em WebView e implementar:
- `SYSTEM_ALERT_WINDOW` para a sobreposição;
- `ForegroundService` com notificação persistente;
- `MediaProjectionManager` para captura, sempre após consentimento do usuário;
- ponte JavaScript para solicitar captura ao código nativo.

Recomendação: criar um projeto Android Studio (Empty Views Activity), copiar os arquivos Kotlin e adaptar o `namespace`/`applicationId`. Testar em versões Android suportadas e revisar as políticas de serviços em primeiro plano. A permissão de sobreposição deve ser solicitada em `Settings.canDrawOverlays`; não pode ser concedida silenciosamente.
