package com.greenfox.repository;

import com.greenfox.model.Account;
import org.springframework.data.repository.CrudRepository;

public interface AccountRepository extends CrudRepository<Account, Long> {
  public Account findAccountByEmail(String email);
}
