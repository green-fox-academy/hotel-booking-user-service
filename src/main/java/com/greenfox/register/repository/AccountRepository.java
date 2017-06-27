package com.greenfox.register.repository;

import com.greenfox.register.model.Account;
import org.springframework.data.repository.CrudRepository;

public interface AccountRepository extends CrudRepository<Account, Long> {
  public Account findAccountByEmail(String email);
}
