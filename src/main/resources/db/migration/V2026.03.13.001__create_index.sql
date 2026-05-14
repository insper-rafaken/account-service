CREATE INDEX idx_email_sha256 ON accounts.accounts (email, password_sha256);
