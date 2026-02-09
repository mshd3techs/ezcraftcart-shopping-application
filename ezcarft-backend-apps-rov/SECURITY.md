# Security Best Practices - EzCraftCart Backend

## 1. Authentication & Authorization

### JWT Implementation
- **Algorithm**: HS256 (HMAC with SHA-256)
- **Expiration**: 24 hours (configurable)
- **Refresh**: Implement refresh token mechanism
- **Storage**: Never store tokens in localStorage (use httpOnly cookies)

### Password Security
- **Hashing**: BCrypt with cost factor 12
- **Validation**: Minimum 8 characters, uppercase, lowercase, number, special char
- **Password Reset**: Time-limited tokens (1 hour expiry)

### RBAC (Role-Based Access Control)
\\\java
@PreAuthorize("hasRole('ADMIN')")
public void adminOnlyMethod() { }

@PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
public void moderatorMethod() { }
\\\

---

## 2. API Security

### Rate Limiting
- **Gateway Level**: 100 requests/minute per IP
- **Authentication**: 10 login attempts/minute
- **Payment**: 5 payment intents/minute per user

### CORS Configuration
\\\yaml
spring:
  web:
    cors:
      allowed-origins: https://ezcraftcart.com
      allowed-methods: GET,POST,PUT,DELETE
      allowed-headers: Authorization,Content-Type
      allow-credentials: true
\\\

### HTTPS Enforcement
\\\yaml
server:
  ssl:
    enabled: true
    key-store: classpath:keystore.p12
    key-store-password: \
    key-store-type: PKCS12
\\\

---

## 3. Data Protection

### Encryption at Rest
- Database: PostgreSQL with TDE (Transparent Data Encryption)
- Sensitive fields: Application-level encryption (AES-256)

### Encryption in Transit
- TLS 1.3 enforced
- Certificate management: Let's Encrypt or AWS ACM

### PII Handling
\\\java
@Column(columnDefinition = "ENCRYPTED_TEXT")
private String creditCardToken;  // Never store actual card numbers

@JsonIgnore  // Never expose in API responses
private String passwordHash;
\\\

---

## 4. Payment Security (PCI-DSS)

### Stripe Integration
- ✅ Card data never touches server
- ✅ Stripe.js handles tokenization
- ✅ Webhook signature verification
- ✅ Idempotency keys for retries

### Webhook Validation
\\\java
String signature = request.getHeader("Stripe-Signature");
Event event = Webhook.constructEvent(payload, signature, webhookSecret);
\\\

---

## 5. Input Validation

### Bean Validation
\\\java
@NotBlank(message = "Email is required")
@Email(message = "Invalid email format")
private String email;

@Size(min = 8, max = 100)
@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*\$")
private String password;
\\\

### SQL Injection Prevention
- ✅ JPA/Hibernate with parameterized queries
- ✅ Never concatenate user input in queries

### XSS Prevention
- ✅ Input sanitization
- ✅ Content Security Policy headers
- ✅ OWASP Java Encoder

---

## 6. Dependency Security

### Vulnerability Scanning
\\\ash
mvn dependency-check:check
\\\

### Automated Updates
- Dependabot enabled
- Weekly security scans
- CVE monitoring

---

## 7. Secrets Management

### Vault Integration
\\\yaml
spring:
  cloud:
    vault:
      uri: http://vault:8200
      token: \
      kv:
        enabled: true
\\\

### Environment Variables
Never commit:
- JWT secrets
- Database passwords
- API keys
- Stripe secrets

---

## 8. Audit Logging

### Security Events
\\\java
@Slf4j
public class AuditLogger {
    public void logLoginAttempt(String username, boolean success) {
        log.info("Login attempt: user={}, success={}, ip={}", 
            username, success, getCurrentIp());
    }
}
\\\

### Compliance
- User actions logged
- Data access tracked
- Retention: 90 days minimum

---

## 9. Security Headers

\\\yaml
spring:
  security:
    headers:
      content-security-policy: "default-src 'self'"
      x-frame-options: DENY
      x-content-type-options: nosniff
      strict-transport-security: max-age=31536000; includeSubDomains
\\\

---

## 10. Incident Response

### Breach Protocol
1. Isolate affected systems
2. Rotate all secrets
3. Notify users (GDPR requirement)
4. Forensic analysis
5. Patch and redeploy

### Monitoring
- Prometheus alerts
- Abnormal login patterns
- Failed authentication spikes
- Unauthorized access attempts

---

## Security Checklist

- [ ] JWT secrets rotated monthly
- [ ] Dependency scan passed
- [ ] HTTPS enforced
- [ ] Rate limiting enabled
- [ ] CORS configured
- [ ] Input validation on all endpoints
- [ ] SQL injection tests passed
- [ ] XSS prevention verified
- [ ] Audit logging enabled
- [ ] Secrets in Vault (not code)
- [ ] Security headers configured
- [ ] PCI-DSS compliance verified
- [ ] GDPR compliance verified
- [ ] Incident response plan documented
- [ ] Penetration testing completed
