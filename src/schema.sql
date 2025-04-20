CREATE TABLE conversation_memory (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id VARCHAR(100),
    message TEXT,
    response TEXT,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE bot_traits (
    user_id VARCHAR(100) PRIMARY KEY,
    tone_style VARCHAR(50),
    personality TEXT,
    memory_enabled BOOLEAN DEFAULT TRUE,
		nickname VARCHAR(50),
		bot_name VARCHAR(50)
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP
);
