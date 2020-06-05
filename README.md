Ansible - Run commands passed as variables
===========================================
Ansible playbook to pass up to 10 commands which will be run on the target nodes.

Requirements
------------
1. Make sure the parameter ```"host_key_checking = False"``` **is uncomment** on the ```/etc/ansible/ansible.cfg``` configuration file, this is to allow running this role without having an inventory.
2. CentOS-7/RHEL-7

Variables
--------------
The following variables are used:
1.  "{{ command_01 }}"
2.  "{{ command_02 }}"
3.  "{{ command_03 }}"
4.  "{{ command_04 }}"
5.  "{{ command_05 }}"
6.  "{{ command_06 }}"
7.  "{{ command_07 }}"
8.  "{{ command_08 }}"
9.  "{{ command_09 }}"
10. "{{ command_10 }}"


Dependencies
------------
None

Running Playbook For 1 Host
---------------------------
****Make sure to check point number 1 on the requirements section.****

1. Download this project to your ansible instance
2. Run the command below, the comma after the ip is **only needed when using only 1 host**
```sh
time sudo ansible-playbook quick_command_pass_as_variable -e 'command_01="your_command"' -i target_ip, -k

e.g:
time sudo ansible-playbook quick_command_pass_as_variable -e 'command_01="ls -lh /var/logs"' -i 1.1.1.1, -k
```

Running Playbook For Multiple Hosts
-----------------------------------
1. Download this project to your ansible instance
2. Run the command below:
```sh
time sudo ansible-playbook main.yml -e 'command_01="your_command"' -i target_ip,another_ip,another_ip -k

e.g:
time sudo ansible-playbook main.yml -e 'command_01="ls -lh /var/logs"' -i 1.1.1.1,1.1.1.2,1.1.1.3 -k
```

Author Information
------------------
Arcsight System-Test team
