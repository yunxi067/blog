CREATE DATABASE IF NOT EXISTS blog_system DEFAULT CHARSET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE blog_system;

CREATE TABLE IF NOT EXISTS user (
    uid INT AUTO_INCREMENT PRIMARY KEY COMMENT '用户ID',
    username VARCHAR(50) UNIQUE NOT NULL COMMENT '用户名',
    password VARCHAR(255) NOT NULL COMMENT '密码',
    email VARCHAR(100) UNIQUE COMMENT '邮箱',
    role INT DEFAULT 2 COMMENT '角色 1=admin 2=user',
    status INT DEFAULT 1 COMMENT '状态 0=frozen 1=normal',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    KEY idx_username (username),
    KEY idx_email (email),
    KEY idx_role (role)
) ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户表';

CREATE TABLE IF NOT EXISTS space (
    sid INT AUTO_INCREMENT PRIMARY KEY COMMENT '空间ID',
    uid INT UNIQUE NOT NULL COMMENT '用户ID',
    ssize_total BIGINT DEFAULT 104857600 COMMENT '总空间大小(100M)',
    ssize_used BIGINT DEFAULT 0 COMMENT '已用空间',
    download_count BIGINT DEFAULT 0 COMMENT '总下载次数',
    status INT DEFAULT 1 COMMENT '状态 0=frozen 1=normal',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    FOREIGN KEY (uid) REFERENCES user(uid) ON DELETE CASCADE,
    KEY idx_uid (uid),
    KEY idx_status (status)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='空间表';

CREATE TABLE IF NOT EXISTS file (
    fid INT AUTO_INCREMENT PRIMARY KEY COMMENT '文件ID',
    uid INT NOT NULL COMMENT '用户ID',
    file_name VARCHAR(255) NOT NULL COMMENT '存储文件名',
    original_name VARCHAR(255) NOT NULL COMMENT '原始文件名',
    file_size BIGINT NOT NULL COMMENT '文件大小',
    file_type VARCHAR(50) COMMENT '文件类型',
    category VARCHAR(50) DEFAULT 'default' COMMENT '分类',
    description TEXT COMMENT '文件描述',
    download_count INT DEFAULT 0 COMMENT '下载次数',
    status INT DEFAULT 1 COMMENT '状态 0=frozen 1=normal',
    file_path VARCHAR(500) NOT NULL COMMENT '物理文件路径',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    FOREIGN KEY (uid) REFERENCES user(uid) ON DELETE CASCADE,
    KEY idx_uid (uid),
    KEY idx_category (category),
    KEY idx_status (status),
    KEY idx_create_time (create_time)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='文件表';

CREATE TABLE IF NOT EXISTS space_apply (
    apply_id INT AUTO_INCREMENT PRIMARY KEY COMMENT '申请ID',
    uid INT NOT NULL COMMENT '用户ID',
    apply_size BIGINT NOT NULL COMMENT '申请大小',
    status INT DEFAULT 0 COMMENT '状态 0=pending 1=approved 2=rejected',
    reason TEXT COMMENT '申请原因',
    reject_reason VARCHAR(500) COMMENT '拒绝原因',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    approved_by INT COMMENT '审核人ID',
    FOREIGN KEY (uid) REFERENCES user(uid) ON DELETE CASCADE,
    KEY idx_uid (uid),
    KEY idx_status (status)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='空间扩容申请表';

CREATE TABLE IF NOT EXISTS follow (
    follow_id INT AUTO_INCREMENT PRIMARY KEY COMMENT '关注ID',
    uid INT NOT NULL COMMENT '关注者ID',
    follow_uid INT NOT NULL COMMENT '被关注者ID',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    UNIQUE KEY unique_follow (uid, follow_uid),
    FOREIGN KEY (uid) REFERENCES user(uid) ON DELETE CASCADE,
    FOREIGN KEY (follow_uid) REFERENCES user(uid) ON DELETE CASCADE,
    KEY idx_uid (uid),
    KEY idx_follow_uid (follow_uid)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户关注表';

CREATE TABLE IF NOT EXISTS notification (
    notification_id INT AUTO_INCREMENT PRIMARY KEY COMMENT '通知ID',
    uid INT NOT NULL COMMENT '通知接收者ID',
    follow_uid INT NOT NULL COMMENT '通知来源用户ID',
    content VARCHAR(500) NOT NULL COMMENT '通知内容',
    status INT DEFAULT 0 COMMENT '状态 0=unread 1=read',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    FOREIGN KEY (uid) REFERENCES user(uid) ON DELETE CASCADE,
    FOREIGN KEY (follow_uid) REFERENCES user(uid) ON DELETE CASCADE,
    KEY idx_uid (uid),
    KEY idx_status (status),
    KEY idx_create_time (create_time)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='通知表';
